package com.verivue.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.verivue.api.article.IArticleClient;
import com.verivue.model.article.dto.ArticleDto;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.pojo.WmChannel;
import com.verivue.model.wemedia.pojo.WmNews;
import com.verivue.model.wemedia.pojo.WmSensitive;
import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.utils.common.SensitiveWordUtil;
import com.verivue.wemedia.mapper.WmChannelMapper;
import com.verivue.wemedia.mapper.WmNewsMapper;
import com.verivue.wemedia.mapper.WmSensitiveMapper;
import com.verivue.wemedia.mapper.WmUserMapper;
import com.verivue.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    @Autowired
    private WmNewsMapper wmNewsMapper;
    @Autowired
    private IArticleClient articleClient;
    @Autowired
    private WmChannelMapper wmChannelMapper;
    @Autowired
    private WmUserMapper wmUserMapper;
    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;


    @Override
    @Async
    public void autoScanWmNews(Long id) {
        // 1. Query the WeMedia News
        WmNews news = wmNewsMapper.selectById(id);

        if (news == null) {
            throw new RuntimeException("WmNewsAutoScanServiceImpl- Article not found");
        }


        if(news.getStatus().equals(WmNews.Status.SUBMIT.getCode())){
            // Get the text and image form the News Content
            Map<String, Object> textAndImages = handleTextAndImages(news);

            boolean isSensitive = handleSensitiveScan((String) textAndImages.get("content"), news);
            if(!isSensitive) return;

            ResponseResult result = saveAppArticle(news);
            if(!result.getCode().equals(200)){
                throw new RuntimeException("WmNewsAutoScanServiceImpl-文章审核，保存app端相关文章数据失败");
            }

            news.setArticleId((Long) result.getData());
            updateWmNews(news, (short)9, "审核成功");
        }
    }

    /**
     * Sensitive Words Scan
     * @param content
     * @param news
     * @return
     */
    private boolean handleSensitiveScan(String content, WmNews news) {
        boolean flag = true;

        //获取所有的敏感词
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(
                Wrappers.<WmSensitive>lambdaQuery()
                        .select(WmSensitive::getSensitives));
        List<String> sensitiveList = wmSensitives.stream()
                .map(WmSensitive::getSensitives)
                .collect(Collectors.toList());

        SensitiveWordUtil.initMap(sensitiveList);

        Map<String, Integer> map = SensitiveWordUtil.matchWords(content);
        if(map.size() > 0){
            updateWmNews(news,(short) 2,"The current article contains prohibited content"+map);
            flag = false;
        }

        return flag;
    }

    /**
     * Get the text and image form the News Content
     * @param news
     * @return
     */
    private Map<String, Object> handleTextAndImages(WmNews news) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> images = new ArrayList<>();

        // Get the text and image form the News Content
        if (StringUtils.isNotBlank(news.getContent())){
            List<Map> maps = JSONArray.parseArray(news.getContent(), Map.class);
            for (Map map : maps) {
                if(map.get("type").equals("text")){
                    stringBuilder.append(map.get("value"));
                }

                if(map.get("type").equals("image")){
                    images.add((String) map.get("value"));
                }
            }
        }

        // Get the cover images
        if(StringUtils.isNotBlank(news.getImages())){
            String[] split = news.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("content", stringBuilder.toString());
        resultMap.put("images", images);
        return resultMap;
    }

    /**
     * Save the Article in app side
     * @param news
     * @return
     */
    public ResponseResult saveAppArticle(WmNews news) {
        ArticleDto articleDto = new ArticleDto();
        BeanUtils.copyProperties(news, articleDto);
        articleDto.setId(null);
        articleDto.setLayout(news.getType());
        WmChannel channel = wmChannelMapper.selectById(news.getChannelId());
        if (channel != null) {
            articleDto.setChannelName(channel.getName());
        }

        articleDto.setAuthorId(news.getUserId().longValue());
        WmUser user = wmUserMapper.selectById(news.getUserId());
        if (user != null) {
            articleDto.setAuthorName(user.getName());
        }

        if (news.getArticleId() != null) {
            articleDto.setId(news.getArticleId());
        }

        articleDto.setCreatedTime(new Date());

        ResponseResult result = articleClient.saveArticle(articleDto);
        return result;
    }

    /**
     * Update Article Content
     * @param news
     * @param status
     * @param reason
     */
    private void updateWmNews(WmNews news, short status, String reason) {
        news.setStatus(status);
        news.setReason(reason);
        wmNewsMapper.updateById(news);
    }
}

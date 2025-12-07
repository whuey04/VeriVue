package com.verivue.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.verivue.api.wemedia.IWeMediaClient;
import com.verivue.article.mapper.ApArticleMapper;
import com.verivue.article.service.HotArticleService;
import com.verivue.common.constants.ArticleConstants;
import com.verivue.common.redis.CacheService;
import com.verivue.model.article.pojo.ApArticle;
import com.verivue.model.article.vo.HotArticleVo;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.wemedia.pojo.WmChannel;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class HotArticleServiceImpl implements HotArticleService {

    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private IWeMediaClient weMediaClient;
    @Autowired
    private CacheService cacheService;

    /**
     * Calculate the Hot Articles
     */
    @Override
    public void calculateHotArticle() {
        // Query the last 5 days data
        Date date = DateTime.now().minusDays(50).toDate();
        List<ApArticle> articleList = apArticleMapper.getArticleListByLast5Days(date);

        // Calculate the score of the article
        List<HotArticleVo> hotArticleList = calHotArticle(articleList);

        // Cache top 30 high-scoring articles for each channel
        cacheHotArticleToRedisByTag(hotArticleList);
    }

    /**
     * Calculate the score for hot article
     * @param articleList
     * @return
     */
    private List<HotArticleVo> calHotArticle(List<ApArticle> articleList) {
        List<HotArticleVo> hotArticleList = new ArrayList<>();
        if (articleList != null && articleList.size() > 0) {
            for (ApArticle article : articleList) {
                HotArticleVo hotArticleVo = new HotArticleVo();
                BeanUtils.copyProperties(article, hotArticleVo);
                Integer score = calculateScore(article);
                hotArticleVo.setScore(score);
                hotArticleList.add(hotArticleVo);
            }
        }
        return hotArticleList;
    }

    private Integer calculateScore(ApArticle article) {
        Integer score = 0;
        if(article.getLikes() != null){
            score += article.getLikes() * ArticleConstants.HOT_ARTICLE_LIKE_WEIGHT;
        }

        if(article.getViews() != null){
            score += article.getViews();
        }

        if(article.getComment() != null){
            score += article.getComment() * ArticleConstants.HOT_ARTICLE_COMMENT_WEIGHT;
        }

        if(article.getCollection() != null){
            score += article.getCollection() * ArticleConstants.HOT_ARTICLE_COLLECTION_WEIGHT;
        }

        return score;
    }

    /**
     * Cache top 30 high-scoring articles for each channel
     * @param hotArticleList
     */
    private void cacheHotArticleToRedisByTag(List<HotArticleVo> hotArticleList) {
        ResponseResult result = weMediaClient.getChannelList();
        if (result.getCode().equals(200)){
            String channelStrInJson = JSON.toJSONString(result.getData());
            List<WmChannel> wmChannels = JSON.parseArray(channelStrInJson, WmChannel.class);
            if (wmChannels != null && wmChannels.size() > 0) {
                for (WmChannel wmChannel : wmChannels) {
                    List<HotArticleVo> hotArticleVos = hotArticleList.stream().filter(x -> x.getChannelId().equals(wmChannel.getId())).collect(Collectors.toList());

                    sortAndCacheArticle(hotArticleVos, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + wmChannel.getId());
                }
            }
        }

        sortAndCacheArticle(hotArticleList, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG);
    }

    /**
     * Sort and Cache the article data
     * @param hotArticleVos
     * @param key
     */
    private void sortAndCacheArticle(List<HotArticleVo> hotArticleVos, String key) {
        hotArticleVos = hotArticleVos.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed()).collect(Collectors.toList());
        if (hotArticleVos.size() > 30) {
            hotArticleVos = hotArticleVos.subList(0, 30);
        }
        cacheService.set(key, JSON.toJSONString(hotArticleVos));
    }
}

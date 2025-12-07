package com.verivue.wemedia.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.common.constants.WemediaConstants;
import com.verivue.common.constants.WmNewsMessageConstants;
import com.verivue.common.exception.CustomException;
import com.verivue.model.wemedia.dto.NewsAuthDto;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.wemedia.dto.WmNewsDto;
import com.verivue.model.wemedia.dto.WmNewsPageReqDto;
import com.verivue.model.wemedia.pojo.WmMaterial;
import com.verivue.model.wemedia.pojo.WmNews;
import com.verivue.model.wemedia.pojo.WmNewsMaterial;
import com.verivue.model.wemedia.pojo.WmUser;
import com.verivue.model.wemedia.vo.WmNewsVo;
import com.verivue.utils.thread.WmThreadLocalUtil;
import com.verivue.wemedia.mapper.WmMaterialMapper;
import com.verivue.wemedia.mapper.WmNewsMapper;
import com.verivue.wemedia.mapper.WmNewsMaterialMapper;
import com.verivue.wemedia.mapper.WmUserMapper;
import com.verivue.wemedia.service.WmNewsAutoScanService;
import com.verivue.wemedia.service.WmNewsService;
import com.verivue.wemedia.service.WmNewsTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements WmNewsService {

    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;
    @Autowired
    private WmNewsTaskService wmNewsTaskService;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private WmUserMapper wmUserMapper;
    @Autowired
    private WmMaterialMapper wmMaterialMapper;

    /**
     * Query all article
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult findAllArticle(WmNewsPageReqDto dto) {
        if(dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        dto.checkParam();
        //Get current user id
        WmUser user = WmThreadLocalUtil.getUser();
        if(user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //Page Query
        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<WmNews> wrapper = new LambdaQueryWrapper<>();
        // Article status query
        if(dto.getStatus() != null){
            wrapper.eq(WmNews::getStatus, dto.getStatus());
        }

        // Channel query
        if(dto.getChannelId() != null){
            wrapper.eq(WmNews::getChannelId, dto.getChannelId());
        }

        // Article's Publish date Query
        if(dto.getBeginPubDate() != null && dto.getEndPubDate() != null){
            wrapper.between(WmNews::getPublishTime, dto.getBeginPubDate(), dto.getEndPubDate());
        }

        //Keyword fuzzy query
        if (StringUtils.isNotBlank(dto.getKeyword())) {
            wrapper.like(WmNews::getTitle, dto.getKeyword());
        }

        //Query the articles of the current user
        wrapper.eq(WmNews::getUserId, user.getId());

        wrapper.orderByDesc(WmNews::getPublishTime);

        page = page(page, wrapper);

        // return result
        ResponseResult result = new PageResponseResult(dto.getPage(), dto.getSize(), (int) page.getTotal());
        result.setData(page.getRecords());

        return result;
    }

    /**
     * Save/Update article or draft
     *
     * @param wmNewsDto
     * @return
     */
    @Override
    public ResponseResult submitArticle(WmNewsDto wmNewsDto) {
        if (wmNewsDto == null || wmNewsDto.getContent() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //Save or update article
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(wmNewsDto, wmNews);
        //Change the cover images from List to String
        if(wmNewsDto.getImages() != null && wmNewsDto.getImages().size() > 0){
            String imageListInStr = StringUtils.join(wmNewsDto.getImages(), ",");
            wmNews.setImages(imageListInStr);
        }

        if(wmNewsDto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
            wmNews.setType(null);
        }

        saveOrUpdateWmNews(wmNews);

        if(wmNewsDto.getStatus().equals(WmNews.Status.NORMAL.getCode())){
            return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
        }

        List<String> materials =  extractUrlInfo(wmNewsDto.getContent());
        saveRelativeInfoForContent(materials,wmNews.getId());

        saveRelativeInfoForCover(wmNewsDto,wmNews,materials);

//        wmNewsAutoScanService.autoScanWmNews(wmNews.getId());
        wmNewsTaskService.addArticleToTask(wmNews.getId(),wmNews.getPublishTime());

        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);

    }

    /**
     * Get Article Details
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetails(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmNews news = getById(id);
        if (news == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        return ResponseResult.okResult(news);
    }

    /**
     * Delete Article
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteArticle(Long id) {
        if(id == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmNews news = getById(id);
        if (news == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"Article does not exist");
        }

        if(news.getStatus().equals(WmNews.Status.PUBLISHED.getCode())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"Article has been published and cannot be deleted");
        }

        removeById(id);
        wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery()
                .eq(WmNewsMaterial::getId, news.getId()));
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Publish or unpublish article
     *
     * @param wmNewsDto
     * @return
     */
    @Override
    public ResponseResult downOrUp(WmNewsDto wmNewsDto) {
        if (wmNewsDto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "Article ID is required");
        }

        // 2. Query Article by Id
        WmNews news = getById(wmNewsDto.getId());
        if (news == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "Article does not exist");
        }

        // 3. Verify article publication status
        if (!news.getStatus().equals(WmNews.Status.PUBLISHED.getCode())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID, "The current article is not in published status, cannot be put on/off shelf");
        }

        // 4. Update the article's enable status
        if(wmNewsDto.getEnable() != null && wmNewsDto.getEnable() > -1 && wmNewsDto.getEnable() < 2){
            update(Wrappers.<WmNews>lambdaUpdate()
                    .set(WmNews::getEnable, wmNewsDto.getEnable())
                    .eq(WmNews::getId, wmNewsDto.getId()));

            // Notify the article service to update article settings
            if (news.getArticleId() != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("articleId", news.getArticleId());
                map.put("enable", wmNewsDto.getEnable());

                kafkaTemplate.send(WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC,JSON.toJSONString(map));
            }
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * ADMIN
     * Get Article Review List in admin side
     *
     * @param newsAuthDto
     * @return
     */
    @Override
    public ResponseResult getArticleReviewList(NewsAuthDto newsAuthDto) {
        if (newsAuthDto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        newsAuthDto.checkParam();

        IPage page=new Page(newsAuthDto.getPage(),newsAuthDto.getSize());

        LambdaQueryWrapper<WmNews> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        if(newsAuthDto.getStatus()!=null){
            lambdaQueryWrapper.eq(WmNews::getStatus, newsAuthDto.getStatus());
        }

        lambdaQueryWrapper.orderByDesc(WmNews::getCreatedTime);
        page = page(page, lambdaQueryWrapper);

        //Get Author Name
        List<WmNews> records = page.getRecords();
        List<WmNewsVo> newsVoList = new ArrayList<>();

        if(records != null && !records.isEmpty()){
            List<Long> userIds = records.stream()
                    .map(WmNews::getUserId)
                    .collect(Collectors.toList());

            if(userIds != null && !userIds.isEmpty()){
                List<WmUser> wmUsers = wmUserMapper.selectBatchIds(userIds);
                Map<Long, String> userMap = wmUsers.stream()
                        .collect(Collectors.toMap(WmUser::getId, WmUser::getName));
                newsVoList = records.stream().map( record ->{
                    WmNewsVo newsVo = new WmNewsVo();
                    BeanUtils.copyProperties(record, newsVo);
                    newsVo.setAuthorName(userMap.get(record.getUserId()));
                    return newsVo;
                }).collect(Collectors.toList());
            }
        }

        ResponseResult result = new PageResponseResult(newsAuthDto.getPage(), newsAuthDto.getSize(), (int) page.getTotal());
        result.setData(newsVoList);
        return result;
    }

    /**
     * Get Article Details in admin side
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleReviewDetails(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmNews news = getById(id);
        if (news == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "Article does not exist");
        }

        WmUser user = wmUserMapper.selectById(news.getUserId());
        WmNewsVo newsVo = new WmNewsVo();
        BeanUtils.copyProperties(news, newsVo);
        if (user != null) {
            newsVo.setAuthorName(user.getName());
        }

        return ResponseResult.okResult(newsVo);
    }

    /**
     * Update the article review status
     *
     * @param dto
     * @param status
     * @return
     */
    @Override
    public ResponseResult updateReviewStatus(NewsAuthDto dto, Short status) {
        if (dto == null || dto.getId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        WmNews news = getById(dto.getId());
        if (news == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST, "Article does not exist");
        }

        news.setStatus(status);
        if(StringUtils.isNotBlank(dto.getMsg())){
            news.setReason(dto.getMsg());
        }
        updateById(news);

        if (status.equals(WemediaConstants.WM_NEWS_AUTH_PASS)){
            ResponseResult result = wmNewsAutoScanService.saveAppArticle(news);
            if(result.getCode().equals(200)){
                news.setArticleId((Long) result.getData());
                news.setStatus(WmNews.Status.PUBLISHED.getCode());
                updateById(news);
            }
        }
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    /**
     * Function 1: If the current cover type is set to "auto", determine the cover type based on image count.
     * Matching rules:
     * 1. If the number of content images is ≥1 and <3 → single image → type 1
     * 2. If the number of content images is ≥3 → multiple images → type 3
     * 3. If there are no content images → no image → type 0
     *
     * Function 2: Save the relationship between cover images and materials
     *
     * @param dto       Data transfer object containing article information
     * @param wmNews    News entity to be updated
     * @param materials List of associated media materials
     */
    private void saveRelativeInfoForCover(WmNewsDto dto, WmNews wmNews, List<String> materials) {

        List<String> images = dto.getImages();

        if(dto.getType().equals(WemediaConstants.WM_NEWS_TYPE_AUTO)){
            if(materials.size() >= 3){
                wmNews.setType(WemediaConstants.WM_NEWS_MANY_IMAGE);
                images = materials.stream().limit(3).collect(Collectors.toList());
            }else if(materials.size() >= 1 && materials.size() < 3){
                wmNews.setType(WemediaConstants.WM_NEWS_SINGLE_IMAGE);
                images = materials.stream().limit(1).collect(Collectors.toList());
            }else {
                wmNews.setType(WemediaConstants.WM_NEWS_NONE_IMAGE);
            }

            if(images != null && images.size() > 0){
                wmNews.setImages(StringUtils.join(images,","));
            }
            updateById(wmNews);
        }
        if(images != null && images.size() > 0){
            saveRelativeInfo(images,wmNews.getId(),WemediaConstants.WM_COVER_REFERENCE);
        }

    }


    /**
     * Handle the relationship between content images and media materials in the article
     * @param materials
     * @param newsId
     */
    private void saveRelativeInfoForContent(List<String> materials, Long newsId) {
        saveRelativeInfo(materials,newsId,WemediaConstants.WM_CONTENT_REFERENCE);
    }


    /**
     * Save Relative info between materials and article to database
     * @param materials
     * @param newsId
     * @param type
     */
    private void saveRelativeInfo(List<String> materials, Long newsId, Short type) {
        if(materials!=null && !materials.isEmpty()){
            List<WmMaterial> dbMaterials = wmMaterialMapper.selectList(Wrappers.<WmMaterial>lambdaQuery().in(WmMaterial::getUrl, materials));

            if(dbMaterials==null || dbMaterials.size() == 0){
                throw new CustomException(AppHttpCodeEnum.MATERIALS_REFERENCE_FAIL);
            }

            if(materials.size() != dbMaterials.size()){
                throw new CustomException(AppHttpCodeEnum.MATERIALS_REFERENCE_FAIL);
            }

            List<Integer> idList = dbMaterials.stream().map(WmMaterial::getId).collect(Collectors.toList());

            wmNewsMaterialMapper.saveRelations(idList,newsId,type);
        }

    }


    /**
     * Extract image information from the article content
     * @param content
     * @return
     */
    private List<String> extractUrlInfo(String content) {
        List<String> materials = new ArrayList<>();

        List<Map> maps = JSON.parseArray(content, Map.class);
        for (Map map : maps) {
            if(map.get("type").equals("image")){
                String imgUrl = (String) map.get("value");
                materials.add(imgUrl);
            }
        }

        return materials;
    }

    @Autowired
    private WmNewsMaterialMapper wmNewsMaterialMapper;

    /**
     * Save or Update Article
     * @param wmNews
     */
    private void saveOrUpdateWmNews(WmNews wmNews) {
        wmNews.setUserId(WmThreadLocalUtil.getUser().getId());
        wmNews.setCreatedTime(new Date());
        wmNews.setSubmitedTime(new Date());
        wmNews.setEnable((short)1);

        if (wmNews.getPublishTime() == null) {
            wmNews.setPublishTime(new Date());
        }

        if(wmNews.getId() == null){
            save(wmNews);
        }else {
            wmNewsMaterialMapper.delete(Wrappers.<WmNewsMaterial>lambdaQuery().eq(WmNewsMaterial::getNewsId,wmNews.getId()));
            updateById(wmNews);
        }

    }
}

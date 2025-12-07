package com.verivue.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.verivue.article.mapper.ApArticleConfigMapper;
import com.verivue.article.mapper.ApArticleContentMapper;
import com.verivue.article.mapper.ApArticleMapper;
import com.verivue.article.service.ApArticleService;
import com.verivue.article.service.ArticleFreemarkerService;
import com.verivue.common.constants.ArticleConstants;
import com.verivue.common.constants.BehaviorConstants;
import com.verivue.common.redis.CacheService;
import com.verivue.model.article.dto.*;
import com.verivue.model.mess.ArticleVisitStreamMess;
import com.verivue.model.article.pojo.ApArticle;
import com.verivue.model.article.pojo.ApArticleConfig;
import com.verivue.model.article.pojo.ApArticleContent;
import com.verivue.model.article.vo.ArticleCommentVo;
import com.verivue.model.article.vo.HotArticleVo;
import com.verivue.model.common.dto.PageResponseResult;
import com.verivue.model.common.dto.ResponseResult;
import com.verivue.model.common.enums.AppHttpCodeEnum;
import com.verivue.model.user.pojo.ApUser;
import com.verivue.model.wemedia.dto.StatisticsDto;
import com.verivue.utils.common.DateUtils;
import com.verivue.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements ApArticleService {

    // Maximum number of items to load per page
    private final static short MAX_PAGE_SIZE = 50;

    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;
    @Autowired
    private ApArticleContentMapper apArticleContentMapper;
    @Autowired
    private ArticleFreemarkerService articleFreemarkerService;
    @Autowired
    private CacheService cacheService;

    /**
     * Load Article with different type in user app
     *
     * @param articleHomeDto
     * @param loadType 1 = Load more, 2 = Load latest
     * @return
     */
    @Override
    public ResponseResult loadArticleWithType(ArticleHomeDto articleHomeDto, Short loadType) {
        // 1. Get requested page size from dto
        Integer pageSize = articleHomeDto.getSize();

        if (pageSize == null || pageSize == 0){
            // 2. Set the default page size as 10
            pageSize = 10;
        }

        // 3. Limit the page size to a maximum allowed value
        pageSize = Math.min(pageSize, MAX_PAGE_SIZE);
        articleHomeDto.setSize(pageSize);

        // 4. Validate loadType, set default to LOAD_MORE if it's not LOAD_MORE or LOAD_NEW
        if(!loadType.equals(ArticleConstants.LOADTYPE_LOAD_MORE)
                && !loadType.equals(ArticleConstants.LOADTYPE_LOAD_NEW)){
            loadType = ArticleConstants.LOADTYPE_LOAD_MORE;
        }

        // 5. Set default tag if tag is empty
        if(StringUtils.isEmpty(articleHomeDto.getTag())){
            articleHomeDto.setTag(ArticleConstants.DEFAULT_TAG);
        }

        // 6. If maxBehotTime is null, set it to current time
        if(articleHomeDto.getMaxBehotTime() == null){
            articleHomeDto.setMaxBehotTime(new Date());
        }

        // 7. If minBehotTime is null, set it to current time
        if (articleHomeDto.getMinBehotTime() == null){
            articleHomeDto.setMinBehotTime(new Date());
        }

        // 8. Query the article list from database
        List<ApArticle> articleList = apArticleMapper.loadArticleList(articleHomeDto, loadType);

        return ResponseResult.okResult(articleList);
    }

    /**
     * Save article
     *
     * @param articleDto
     * @return
     */
    @Override
    public ResponseResult saveArticle(ArticleDto articleDto) {

        if (articleDto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApArticle article = new ApArticle();
        BeanUtils.copyProperties(articleDto, article);

        if (articleDto.getId() == null){
            //Save Article
            save(article);

            //Save article configuration
            ApArticleConfig articleConfig = new ApArticleConfig(article.getId());
            apArticleConfigMapper.insert(articleConfig);

            //Save article content
            ApArticleContent articleContent = new ApArticleContent();
            articleContent.setArticleId(article.getId());
            articleContent.setContent(articleDto.getContent());
            apArticleContentMapper.insert(articleContent);
        }
        else {
            //Update Article
            updateById(article);

            //Update Article content
            ApArticleContent articleContent = apArticleContentMapper.selectOne(
                    Wrappers.<ApArticleContent>lambdaQuery()
                            .eq(ApArticleContent::getArticleId, article.getId())
            );

            articleContent.setContent(articleDto.getContent());
            apArticleContentMapper.updateById(articleContent);

            //Update Article config
            ApArticleConfig articleConfig = apArticleConfigMapper.selectOne(
                    Wrappers.<ApArticleConfig>lambdaQuery()
                            .eq(ApArticleConfig::getArticleId, article.getId()));
            
            articleConfig.setIsDown(false);
            apArticleConfigMapper.updateById(articleConfig);
        }

        articleFreemarkerService.generateArticleToMinIO(article, articleDto.getContent());


        return ResponseResult.okResult(article.getId());
    }

    /**
     * Display all the article behavior in app side
     *
     * @param dto
     * @return
     */
    @Override
    public ResponseResult displayArticleBehavior(ArticleInfoDto dto) {
        if (dto == null || dto.getArticleId() == null || dto.getAuthorId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        boolean isFollow = false, isLike = false, isDislike = false, isFavorite = false;

        ApUser user = AppThreadLocalUtil.getUser();
        if (user != null) {
            //喜欢行为
            String likeJson = (String) cacheService.hGet(
                    BehaviorConstants.LIKE_BEHAVIOR + dto.getArticleId().toString(),
                    user.getId().toString());
            if (StringUtils.isNotBlank(likeJson)) {
                isLike = true;
            }
            //不喜欢的行为
            String dislikeJson = (String) cacheService.hGet(
                    BehaviorConstants.UN_LIKE_BEHAVIOR + dto.getArticleId().toString(),
                    user.getId().toString());
            if (StringUtils.isNotBlank(dislikeJson)) {
                isDislike = true;
            }
            //是否收藏
            String collctionJson = (String) cacheService.hGet(BehaviorConstants.COLLECTION_BEHAVIOR + user.getId(), dto.getArticleId().toString());
            if(StringUtils.isNotBlank(collctionJson)){
                isFavorite = true;
            }
            //是否关注
            Double score = cacheService.zScore(BehaviorConstants.APUSER_FOLLOW_RELATION + user.getId(), dto.getAuthorId().toString());
            System.out.println(score);
            if(score != null){
                isFollow = true;
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("isfollow", isFollow);
        map.put("islike", isLike);
        map.put("isunlike", isDislike);
        map.put("iscollection", isFavorite);

        return ResponseResult.okResult(map);
    }

    /**
     * Load Article with different type in user app
     *
     * @param articleHomeDto
     * @param loadType
     * @param firstPage
     * @return
     */
    @Override
    public ResponseResult loadArticleWithType2(ArticleHomeDto articleHomeDto, Short loadType, boolean firstPage) {
        if(firstPage){
            String jsonStr = cacheService.get(ArticleConstants.HOT_ARTICLE_FIRST_PAGE + articleHomeDto.getTag());
            if (StringUtils.isNotBlank(jsonStr)) {
                List<HotArticleVo> hotArticleList = JSON.parseArray(jsonStr, HotArticleVo.class);
                return ResponseResult.okResult(hotArticleList);
            }
        }
        return loadArticleWithType(articleHomeDto, loadType);
    }

    /**
     * Update article score and cache hot articles
     *
     * @param mess
     */
    @Override
    public void updateArticleScore(ArticleVisitStreamMess mess) {

        ApArticle article = updateArticleByMess(mess);

        Integer score = calculateArticleScore(article);
        score = score * 3;

        // Update hot article data for the current article's channel
        updateDataToRedis(article, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + article.getChannelId());

        updateDataToRedis(article, score, ArticleConstants.HOT_ARTICLE_FIRST_PAGE + ArticleConstants.DEFAULT_TAG);

    }

    /**
     * Get Article Comments
     *
     * @param dto
     * @return
     */
    @Override
    public PageResponseResult getArticleComments(ArticleCommentDto dto) {
        Integer currentPage = dto.getPage();
        dto.setPage((dto.getPage() - 1) * dto.getSize());
        List<ArticleCommentVo> list = apArticleMapper.getArticleComments(dto);
        int count = apArticleMapper.getArticlesCommentsCount(dto);

        PageResponseResult responseResult = new PageResponseResult(currentPage, dto.getSize(), count);
        responseResult.setData(list);
        return responseResult;

    }

    @Override
    public ResponseResult getLikesAndCollections(Long wmUserId, Date beginDate, Date endDate) {
        ArticleStatusDto status = apArticleMapper.getLikesAndCollections(wmUserId, beginDate, endDate);
        Map<String, Object> map = new HashMap<>();
        map.put("likesNum", status.getLikes());
        map.put("collectNum", status.getCollections());
        map.put("publishNum", status.getNewsCount());

        return ResponseResult.okResult(map);
    }

    @Override
    public PageResponseResult newPage(StatisticsDto dto) {

        Date beginDate = DateUtils.stringToDate(dto.getBeginDate());
        Date endDate = DateUtils.stringToDate(dto.getEndDate());

        dto.checkParam();

        IPage page = new Page(dto.getPage(), dto.getSize());
        LambdaQueryWrapper<ApArticle> lambdaQueryWrapper = Wrappers.<ApArticle>lambdaQuery()
                .eq(ApArticle::getAuthorId, dto.getWmUserId())
                .between(ApArticle::getPublishTime,beginDate, endDate)
                .select(ApArticle::getId,ApArticle::getTitle,ApArticle::getLikes,ApArticle::getCollection,ApArticle::getComment,ApArticle::getViews);

        lambdaQueryWrapper.orderByDesc(ApArticle::getPublishTime);

        page = page(page,lambdaQueryWrapper);

        PageResponseResult responseResult = new PageResponseResult(dto.getPage(),dto.getSize(),(int)page.getTotal());
        responseResult.setData(page.getRecords());

        return responseResult;
    }

    /**
     * Update number of article behavior
     * @param mess
     * @return
     */
    private ApArticle updateArticleByMess(ArticleVisitStreamMess mess) {
        ApArticle article = getById(mess.getArticleId());

        article.setCollection(article.getCollection() == null ? 0 : article.getCollection() + mess.getCollect());

        article.setComment(article.getComment() == null ? 0 : article.getComment() + mess.getComment());

        article.setLikes(article.getLikes() == null ? 0 : article.getLikes() + mess.getLike());

        article.setViews(article.getViews() == null ? 0 : article.getViews() + mess.getView());

        updateById(article);

        return article;
    }

    /**
     * Calculate Article Score
     * @param article
     * @return
     */
    private Integer calculateArticleScore(ApArticle article) {
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
     * Update new hot article data to redis
     * @param article
     * @param score
     * @param source
     */
    private void updateDataToRedis(ApArticle article, Integer score, String source) {
        String articleList = cacheService.get(source);

        if (StringUtils.isNotBlank(articleList)) {
            List<HotArticleVo> hotArticleVoList = JSON.parseArray(articleList, HotArticleVo.class);

            boolean flag = true;

            // Just update score, if the article exist
            for (HotArticleVo hotArticleVo : hotArticleVoList) {
                if (hotArticleVo.getId().equals(article.getId())) {
                    hotArticleVo.setScore(score);
                    flag = false;
                    break;
                }
            }

            // If cache is empty, retrieve the lowest-scored article in cache and compare;
            // replace it if the current article has a higher score
            if (flag) {
                if(hotArticleVoList.size() >= 30){
                    hotArticleVoList = hotArticleVoList.stream()
                            .sorted(Comparator.comparing(HotArticleVo::getScore).reversed())
                            .collect(Collectors.toList());
                    HotArticleVo lastHot = hotArticleVoList.get(hotArticleVoList.size() - 1);
                    if(lastHot.getScore() < score){
                        hotArticleVoList.remove(lastHot);
                        HotArticleVo newHot = new HotArticleVo();
                        BeanUtils.copyProperties(article, newHot);
                        newHot.setScore(score);
                        hotArticleVoList.add(newHot);
                    }
                }
                else {
                    HotArticleVo newHot = new HotArticleVo();
                    BeanUtils.copyProperties(article, newHot);
                    newHot.setScore(score);
                    hotArticleVoList.add(newHot);
                }
            }

            // Update to redis
            hotArticleVoList = hotArticleVoList.stream().sorted(Comparator.comparing(HotArticleVo::getScore).reversed())
                    .collect(Collectors.toList());

            cacheService.set(source, JSON.toJSONString(hotArticleVoList));
        }
    }
}

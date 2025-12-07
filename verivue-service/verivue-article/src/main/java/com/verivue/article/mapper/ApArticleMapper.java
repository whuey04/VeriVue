package com.verivue.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.verivue.model.article.dto.ArticleCommentDto;
import com.verivue.model.article.dto.ArticleHomeDto;
import com.verivue.model.article.dto.ArticleStatusDto;
import com.verivue.model.article.pojo.ApArticle;
import com.verivue.model.article.vo.ArticleCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ApArticleMapper extends BaseMapper<ApArticle> {

    List<ApArticle> loadArticleList(@Param("dto") ArticleHomeDto dto, @Param("type") Short type);

    List<ApArticle> getArticleListByLast5Days(@Param("dayParam") Date dayParam);

    List<ArticleCommentVo> getArticleComments(@Param("dto") ArticleCommentDto dto);

    int getArticlesCommentsCount(@Param("dto") ArticleCommentDto dto);

    ArticleStatusDto getLikesAndCollections(@Param("wmUserId") Long wmUserId, @Param("beginDate") Date beginDate, @Param("endDate") Date endDate);
}

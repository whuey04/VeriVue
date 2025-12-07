package com.verivue.article.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.verivue.article.mapper.ApArticleContentMapper;
import com.verivue.article.mapper.ApArticleMapper;
import com.verivue.article.service.ApArticleService;
import com.verivue.article.service.ArticleFreemarkerService;
import com.verivue.common.constants.ArticleConstants;
import com.verivue.file.service.FileStorageService;
import com.verivue.model.article.pojo.ApArticle;
import com.verivue.model.search.vo.SearchArticleVo;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class ArticleFreemarkerServiceImpl implements ArticleFreemarkerService {

    @Autowired
    private Configuration configuration;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ApArticleMapper apArticleMapper;
    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    /**
     * Generate Article file by Freemarker and upload it to minIO
     *
     * @param apArticle
     * @param content
     */
    @Async
    @Override
    public void generateArticleToMinIO(ApArticle apArticle, String content) {

        if (StringUtils.isNotBlank(content)) {
            Template template = null;
            StringWriter stringWriter = new StringWriter();
            try {
                template = configuration.getTemplate("article.ftl");

                Map<String, Object> contentMap = new HashMap<>();
                contentMap.put("content", JSONArray.parseArray(content));
                template.process(contentMap,stringWriter);
            }catch (Exception e) {
                e.printStackTrace();
            }


            InputStream in = new ByteArrayInputStream(stringWriter.toString().getBytes());
            String path = fileStorageService.uploadHtmlFile("",apArticle.getId() + ".html",in);


            apArticleMapper.update(Wrappers.<ApArticle>lambdaUpdate().eq(ApArticle::getId,apArticle.getId()).set(ApArticle::getStaticUrl,path));


            createArticleElasticSearchIndex(apArticle,content,path);
        }
    }

    /**
     * Create Index
     * @param apArticle
     * @param content
     * @param path
     */
    private void createArticleElasticSearchIndex(ApArticle apArticle, String content, String path) {
        SearchArticleVo searchArticleVo = new SearchArticleVo();
        BeanUtils.copyProperties(apArticle, searchArticleVo);
        searchArticleVo.setContent(content);
        searchArticleVo.setStaticUrl(path);

        kafkaTemplate.send(
                ArticleConstants.ARTICLE_ES_SYNC_TOPIC,
                JSON.toJSONString(searchArticleVo)
        );
    }
}

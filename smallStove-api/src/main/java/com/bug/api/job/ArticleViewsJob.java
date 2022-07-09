package com.bug.api.job;


import com.bug.framework.utils.RedisCache;
import com.bug.system.domain.entity.Article;
import com.bug.system.domain.vo.ArticleVo;
import com.bug.system.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 定时将redis中的views更新到数据库中（likes还未实现）
 */
@Component
public class ArticleViewsJob {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisCache redisCache;
    @Resource
    private ArticleService articleService;

    /**
     * 每三十分钟收集一次redis中文章数据
     * 30 0/30 * * * ?
     */
    @Scheduled(cron = "30 0/30 * * * ?")
    public void updateViewsLikes() {
        logger.info("定时任务启动了");

        // 获取redis中的每一篇文章的数据
        Map<Object, Object> map = redisCache.hmGet(redisCache.getArticleVoKey());
        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            ArticleVo articleVo = (ArticleVo) entry.getValue();
            Article article = articleService.getById(articleVo.getArticleId());
            article.setViewsNumber(articleVo.getViewsNumber());
            article.setLikesNumber(articleVo.getLikesNumber());
            articleService.saveOrUpdate(article);
        }

    }
}

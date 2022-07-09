package com.bug.api.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bug.framework.constant.SystemConstant;
import com.bug.framework.utils.BeanCopyUtils;
import com.bug.framework.utils.RedisCache;
import com.bug.system.domain.entity.Article;
import com.bug.system.domain.vo.ArticleVo;
import com.bug.system.service.ArticleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * springBoot的预处理类
 * 在程序启动时, 预先将文章列表第一页中文章的浏览量和点赞数存储到redis中, key=浏览量-点赞数
 */
@Component
public class PretreatmentRunner implements CommandLineRunner {
    @Resource
    private ArticleService articleService;
    @Resource
    private RedisCache redisCache;

    @Override
    public void run(String... args) {
        // 查询文章列表前十页数据
        Page<Article> page = new Page<>(SystemConstant.PAGE_CURRENT, SystemConstant.PAGE_SIZE * 10);

        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        // 查询的文章必须是公开的
        articleWrapper.eq(Article::getStatus, SystemConstant.ARTICLE_STATUS_PUBLIC);
        // 按照时间升序排列
        articleWrapper.orderByDesc(Article::getCreateTime);
        // 分页查询
        articleService.page(page, articleWrapper);

        List<ArticleVo> articleVoList = BeanCopyUtils.copyAllBean(page.getRecords(), ArticleVo.class);

        // 将前十页中每一篇文章存入redis中
        Map<String, Object> map = new HashMap<>();
        for (ArticleVo articleVo : articleVoList) {
            Long articleId = articleVo.getArticleId();
            map.put(articleId.toString(), articleVo);
        }
        // 默认过期时间为12小时
        redisCache.hmSet(redisCache.getArticleVoKey(), map, redisCache.getArticleExpirationTime());
    }
}

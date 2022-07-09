package com.bug.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.framework.models.Result;
import com.bug.system.domain.entity.Article;
import com.bug.system.domain.vo.ArticleVo;
import com.bug.system.domain.vo.PageVo;

import java.util.List;

/**
 * @author 源
 * @description 针对表【sys_article(文章表)】的数据库操作Service
 * @createDate 2022-06-09 20:06:28
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页查询最新的文章缩略列表
     */
    Result<PageVo<ArticleVo>> getArticleList(Integer currentPage, Integer pageSize, Long typeId, Long userId);

    /**
     * 查询指定文章详情
     */
    Result<ArticleVo> getArticleDetails(Long articleId);

    /**
     * 发表文章
     */
    Result<?> publishArticle(ArticleVo articleVo);

    /**
     * 修改文章
     */
    Result<?> updateArticle(ArticleVo articleVo, List<Long> typeIdList);

    /**
     * 删除文章
     */
    Result<?> deleteArticle(Long articleId);

    /**
     * 更新文章浏览量
     */
    Result<?> updateViews(Long articleId);

    /**
     * 更新文章点赞数
     */
    Result<?> updateLikes(Long articleId);

    Result<?> getTags();
}

package com.bug.api.controller.common;

import com.bug.framework.aop.annotation.SystemLog;
import com.bug.framework.models.Result;
import com.bug.system.domain.vo.ArticleVo;
import com.bug.system.domain.vo.PageVo;
import com.bug.system.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "文章接口")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;

    // TODO: 2022/6/11 待修改，优先从redis中读取，其次从数据库中读
    @SystemLog(business = "获取文章列表")
    @ApiOperation(value = "文章列表", notes = "分页查询最新的文章缩略列表")
    @GetMapping({"/anonymous/article-list"})
    public Result<PageVo<ArticleVo>> getArticleList(
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "typeId", required = false) Long typeId,
            @RequestParam(value = "userId", required = false) Long userId) {

        return articleService.getArticleList(currentPage, pageSize, typeId, userId);
    }

    @ApiOperation(value = "文章详情", notes = "查询文章详情，redis优先，数据库其次")
    @GetMapping({"/anonymous/article-details/{articleId}"})
    public Result<ArticleVo> getArticleDetails(@PathVariable(value = "articleId", required = false) Long articleId) {
        return articleService.getArticleDetails(articleId);
    }

    // TODO: 2022/6/10 发表文章待测试, 请多加考虑，点赞数、浏览量无法主动设置
    @ApiOperation(value = "发帖", notes = "发表文章，功能未实现")
    @PostMapping({"/article"})
    public Result<?> publishArticle(@RequestBody ArticleVo articleVo) {
        return articleService.publishArticle(articleVo);
    }

    // TODO: 2022/6/10 修改文章待测试,请多加考虑，点赞数、浏览量无法主动修改, 考虑一下请求是PUT还是POST
    @ApiOperation(value = "改帖", notes = "修改文章，功能未实现")
    @PutMapping({"/article"})
    public Result<?> updateArticle(@RequestBody ArticleVo articleVo,
                                   @RequestParam(value = "typeIdList") List<Long> typeIdList) {
        return articleService.updateArticle(articleVo, typeIdList);
    }

    @ApiOperation(value = "删帖", notes = "删除已发表文章")
    @DeleteMapping({"/article/{articleId}"})
    public Result<?> deleteArticle(@PathVariable("articleId") Long articleId) {
        return articleService.deleteArticle(articleId);
    }

    @ApiOperation(value = "浏览", notes = "当用户浏览了帖子，就要修改帖子的浏览量")
    @PutMapping("/views/{articleId}")
    public Result<?> updateViews(@PathVariable("articleId") Long articleId) {
        return articleService.updateViews(articleId);
    }

    @ApiOperation(value = "点赞", notes = "当用户（取消）点赞帖子，就要修改帖子的点赞数")
    @PutMapping("/likes/{articleId}")
    public Result<?> updateLikes(@PathVariable("articleId") Long articleId) {
        return articleService.updateLikes(articleId);
    }

    @ApiOperation(value = "文章标签(分类)", notes = "当用户发帖时，需要选择文章的标签")
    @GetMapping("/tags")
    public Result<?> getTags() {
        return articleService.getTags();
    }
}

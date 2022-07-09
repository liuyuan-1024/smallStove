package com.bug.api.controller.common;


import com.bug.framework.models.Result;
import com.bug.system.domain.vo.CommentVo;
import com.bug.system.domain.vo.PageVo;
import com.bug.system.service.CommentService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "评论接口")
@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    @GetMapping("/anonymous/comment-list/{articleId}")
    public Result<PageVo<CommentVo>> getCommentList(
            @PathVariable("articleId") Long articleId,
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") Integer currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        return commentService.getCommentList(articleId, currentPage, pageSize);
    }

    @PostMapping("/comment")
    public Result<?> publishComment(@RequestBody CommentVo commentVo) {
        return commentService.publishComment(commentVo);
    }

    @DeleteMapping("/user/comment/{commentId}")
    public Result<?> deleteComment(@PathVariable("commentId") Long commentId) {
        return commentService.deleteComment(commentId);
    }

}

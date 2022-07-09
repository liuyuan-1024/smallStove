package com.bug.system.service;

import com.bug.framework.models.Result;
import com.bug.system.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.system.domain.vo.CommentVo;
import com.bug.system.domain.vo.PageVo;

/**
 * @author 源
 * @description 针对表【sys_comment(评论表)】的数据库操作Service
 * @createDate 2022-06-13 13:49:04
 */
public interface CommentService extends IService<Comment> {

    Result<PageVo<CommentVo>> getCommentList(Long articleId, Integer currentPage, Integer pageSize);

    Result<?> publishComment(CommentVo commentVo);

    Result<?> deleteComment(Long commentId);
}

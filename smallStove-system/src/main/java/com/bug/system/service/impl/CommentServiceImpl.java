package com.bug.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.framework.constant.SystemConstant;
import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.exception.SystemException;
import com.bug.framework.utils.BeanCopyUtils;
import com.bug.framework.utils.KeyWordSearcher;
import com.bug.framework.utils.SecurityUtils;
import com.bug.system.domain.entity.Comment;
import com.bug.system.domain.vo.CommentVo;
import com.bug.system.domain.vo.PageVo;
import com.bug.system.mapper.CommentMapper;
import com.bug.system.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 源
 * @description 针对表【sys_comment(评论表)】的数据库操作Service实现
 * @createDate 2022-06-13 13:49:04
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private CommentMapper commentMapper;

    /**
     * 递归查询多级评论
     */
    private void getChildComment(CommentVo parent) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, parent.getCommentId());
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> childCommentList = commentMapper.selectList(queryWrapper);

        if (!ObjectUtils.isEmpty(childCommentList)) {
            List<CommentVo> childCommentVoList = BeanCopyUtils.copyAllBean(childCommentList, CommentVo.class);
            parent.setChildList(childCommentVoList);
            for (CommentVo child : childCommentVoList) {
                getChildComment(child);
            }
        }
    }

    /**
     * 递归查询二级子评论
     */
    private void getChildComment(CommentVo root, List<CommentVo> list) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, root.getCommentId());
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> childCommentList = commentMapper.selectList(queryWrapper);

        if (!ObjectUtils.isEmpty(childCommentList)) {
            List<CommentVo> childCommentVoList = BeanCopyUtils.copyAllBean(childCommentList, CommentVo.class);
            for (CommentVo child : childCommentVoList) {
                list.add(child);
                getChildComment(child, list);
            }
        }
    }

    @Override
    public Result<PageVo<CommentVo>> getCommentList(Long articleId, Integer currentPage, Integer pageSize) {
        // 查询文章所有根评论
        LambdaQueryWrapper<Comment> rootWrapper = new LambdaQueryWrapper<>();
        rootWrapper.eq(Comment::getArticleId, articleId);
        rootWrapper.eq(Comment::getParentId, SystemConstant.COMMENT_ROOT);
        rootWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> rootList = commentMapper.selectList(rootWrapper);

        if (ObjectUtils.isEmpty(rootList)) {
            return new ResultBuilder().success(ResultEnum.COMMENT_LIST_FAIL);
        }

        // 查询根评论下的所有子评论, 并将子评论设置到根评论的属性中
        List<CommentVo> rootVoList = BeanCopyUtils.copyAllBean(rootList, CommentVo.class);
        for (CommentVo root : rootVoList) {
            ArrayList<CommentVo> list = new ArrayList<>(10);
            this.getChildComment(root, list);
            root.setChildList(list);
        }

        PageVo<CommentVo> pageVo = new PageVo<>();
        pageVo.setRows(rootVoList);
        pageVo.setTotal((long) rootVoList.size());

        return new ResultBuilder().success(ResultEnum.COMMENT_LIST_SUCCESS, pageVo);
    }

    @Override
    public Result<?> publishComment(CommentVo commentVo) {
        // 判断评论内容是否含有敏感词
        List<String> retrieve = KeyWordSearcher.retrieve(commentVo.getContent());
        if (!ObjectUtils.isEmpty(retrieve)) {
            logger.info("含有敏感词: {}", retrieve);
            return new ResultBuilder().error(ResultEnum.PUBLISH_FAIL, retrieve);
        }

        Comment comment = BeanCopyUtils.copySingleBean(commentVo, Comment.class);
        comment.setUserId(SecurityUtils.getUserId());
        comment.setCreateBy(SecurityUtils.getUsername());
        comment.setCreateTime(new Date());
        // 将comment添加到数据库
        if (commentMapper.insert(comment) <= 0) {
            // 添加失败抛出异常
            throw new SystemException(ResultEnum.COMMENT_PUBLISH_FAIL);
        }
        return new ResultBuilder().success(ResultEnum.COMMENT_PUBLISH_SUCCESS);
    }

    @Override
    public Result<?> deleteComment(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        Long userId = SecurityUtils.getUserId();

        // 删除的安全校验, 管理员权限
        if (ObjectUtils.isEmpty(comment) || SecurityUtils.isAdmin(userId) || !comment.getUserId().equals(userId)) {
            logger.error("有人试图篡改数据库评论信息!");
            return new ResultBuilder().error(ResultEnum.COMMENT_DELETE_FAIL);
        }

        CommentVo commentVo = BeanCopyUtils.copySingleBean(comment, CommentVo.class);
        ArrayList<CommentVo> childList = new ArrayList<>(10);
        this.getChildComment(commentVo, childList);

        List<Long> childIdList = childList.stream()
                .map(CommentVo::getCommentId)
                .collect(Collectors.toList());
        childIdList.add(commentId);
        // 删除该评论及其子评论
        if (commentMapper.deleteBatchIds(childIdList) <= 0) {
            throw new SystemException(ResultEnum.COMMENT_DELETE_FAIL);
        }

        return new ResultBuilder().success(ResultEnum.COMMENT_DELETE_SUCCESS);
    }
}





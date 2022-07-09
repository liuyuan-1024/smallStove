package com.bug.system.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Data
public class CommentVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 子评论集合
     */
    private List<CommentVo> childList;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 父评论ID（0代表无父评论，即根评论）
     */
    private Long parentId;

    /**
     * 评论者ID
     */
    private Long userId;

    /**
     * 被评论者ID
     */
    private Long toUserId;
}

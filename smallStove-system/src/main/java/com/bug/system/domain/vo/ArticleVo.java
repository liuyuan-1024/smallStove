package com.bug.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ArticleVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 作者ID
     */
    private Long userId;

    /**
     * 作者昵称
     */
    private String nickName;

    /**
     * 作者头像
     */
    private String avatar;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章缩略图
     */
    private String thumbnail;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 文章类型
     */
    private List<TypeVo> typeVoList;

    /**
     * 文章浏览量
     */
    private Integer viewsNumber;

    /**
     * 文章点赞数
     */
    private Integer likesNumber;

    /**
     * 是否允许评论（0代表允许, 1代表禁止）
     */
    private String allowComment;

    /**
     * 文章状态（0代表公开, 1代表私密）
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;
}

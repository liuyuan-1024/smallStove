package com.bug.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章表
 *
 * @TableName sys_article
 */
@TableName(value = "sys_article")
@Data
public class Article implements Serializable {
    /**
     * 文章ID
     */
    @TableId(type = IdType.AUTO)
    private Long articleId;

    /**
     * 作者ID
     */
    private Long userId;

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
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

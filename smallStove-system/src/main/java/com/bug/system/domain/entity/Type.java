package com.bug.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 类别表
 * @TableName sys_type
 */
@TableName(value ="sys_type")
@Data
public class Type implements Serializable {
    /**
     * 类别ID
     */
    @TableId(type = IdType.AUTO)
    private Long typeId;

    /**
     * 类别名称
     */
    private String typeName;

    /**
     * 类别状态（0代表正常, 1代表停用）
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
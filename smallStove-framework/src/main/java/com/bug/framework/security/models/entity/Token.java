package com.bug.framework.security.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: token实体类
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 权限令牌
     */
    private String access_token;

    /**
     * 刷新令牌
     */
    private String refresh_token;
}

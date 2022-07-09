package com.bug.framework.security.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * token实体类
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

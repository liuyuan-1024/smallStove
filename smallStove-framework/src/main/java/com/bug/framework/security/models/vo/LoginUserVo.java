package com.bug.framework.security.models.vo;

import com.bug.framework.security.models.entity.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: LoginUser的视图对象
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginUserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别
     */
    private Integer sex;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 令牌
     */
    private Token token;
}

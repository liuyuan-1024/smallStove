package com.bug.framework.security.models.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 当前登录系统的用户
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class LoginUser implements UserDetails {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

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

    /**
     * 最后登录IP
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    private Date loginDate;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 依据用户权限生成的Security权限对象列表
     */
    @JSONField(serialize = false)
    private Set<GrantedAuthority> authorities;

    public LoginUser(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public LoginUser(Long id, String username, String password, String nickName, String email, String mobile,
                     Integer sex, String avatar, Set<String> permissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.mobile = mobile;
        this.sex = sex;
        this.avatar = avatar;
        this.permissions = permissions;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (!ObjectUtils.isEmpty(authorities)) {
            return authorities;
        }

        authorities = new HashSet<>();
        for (String permission : this.permissions) {
            if (permission != null) {
                JSONObject parse = JSON.parseObject(permission);
                String roleKey = parse.get("roleKey").toString();
                authorities.add(new SimpleGrantedAuthority(roleKey));
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

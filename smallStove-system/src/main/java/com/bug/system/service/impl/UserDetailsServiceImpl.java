package com.bug.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.bug.framework.security.domain.entity.LoginUser;
import com.bug.system.domain.entity.SysRole;
import com.bug.system.domain.entity.SysUser;
import com.bug.system.service.SysRoleService;
import com.bug.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService userService;
    @Resource
    private SysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByUsername(username);

        if (ObjectUtils.isEmpty(sysUser)) {
            log.info("登录用户：{} 不存在.", username);
            throw new UsernameNotFoundException("用户不存在");
        }

        return createLoginUser(sysUser);
    }

    private UserDetails createLoginUser(SysUser sysUser) {
        Set<String> permissions = new HashSet<>();

        List<SysRole> roleSet = roleService.getRoleByUserId(sysUser.getId());
        for (SysRole sysRole : roleSet) {
            permissions.add(JSON.toJSONString(sysRole));
        }

        for (String permission : permissions) {
            Object parse = JSON.parse(permission);
            System.out.println("parse = " + parse);
        }

        // TODO: 2022/6/3 获取登录IP和登陆时间

        return new LoginUser(sysUser.getId(), sysUser.getUsername(), sysUser.getPassword(), sysUser.getNickname(),
                sysUser.getEmail(), sysUser.getMobile(), sysUser.getSex(), sysUser.getAvatar(), permissions);
    }
}

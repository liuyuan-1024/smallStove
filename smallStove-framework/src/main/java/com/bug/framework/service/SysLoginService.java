package com.bug.framework.service;

import com.bug.framework.models.Result;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 用户登录业务逻辑接口
 */
public interface SysLoginService {

    Result<?> login(String username, String password);

    Result<?> logout();
}

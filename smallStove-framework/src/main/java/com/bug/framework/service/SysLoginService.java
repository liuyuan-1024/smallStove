package com.bug.framework.service;

import com.bug.framework.models.Result;

public interface SysLoginService {

    Result<?> login(String username, String password);

    Result<?> logout();
}

package com.bug.framework.security.handler;

import com.alibaba.fastjson.JSON;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 */
@Component
public class GoAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        WebUtils.renderJson(response, JSON.toJSONString(ResultEnum.LOGIN_FAIL));
    }
}

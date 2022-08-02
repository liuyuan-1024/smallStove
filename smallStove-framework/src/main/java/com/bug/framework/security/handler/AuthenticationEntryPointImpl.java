package com.bug.framework.security.handler;

import com.alibaba.fastjson.JSON;
import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.utils.WebUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: SpringSecurity的自定义认证失败处理器 (身份验证入口点)
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        //将要响应会客户端的数据
        ResultBuilder resultBuilder = new ResultBuilder();
        Result<String> result = resultBuilder.error(ResultEnum.UNAUTHORIZED, e.getMessage());
        String jsonString = JSON.toJSONString(result);

        //处理异常，响应JSON数据结果
        WebUtils.renderJson(response, jsonString);
    }
}

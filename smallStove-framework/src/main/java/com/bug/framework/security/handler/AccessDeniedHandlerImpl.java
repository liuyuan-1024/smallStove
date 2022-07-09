package com.bug.framework.security.handler;

import com.alibaba.fastjson.JSON;
import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * SpringSecurity的自定义授权失败处理器
 * 拒绝访问处理程序
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        //将要响应会客户端的数据
        ResultBuilder resultBuilder = new ResultBuilder();
        Result<String> result = resultBuilder.error(ResultEnum.FORBIDDEN, "用户权限不足");
        String jsonString = JSON.toJSONString(result);

        WebUtils.renderJson(response, jsonString);
    }
}

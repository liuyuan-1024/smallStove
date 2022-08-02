package com.bug.framework.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: Web工具类
 */
public class WebUtils {

    /**
     * 响应客户端字符串数据（JSON格式）
     */
    public static void renderJson(HttpServletResponse response, String string) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(string);
    }
}

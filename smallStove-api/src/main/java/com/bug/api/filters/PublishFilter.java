package com.bug.api.filters;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: LRJ
 * @Date: 2022/7/10 11:53
 * @Description: 针对发帖, 评论等以"publish"开头的请求路径进行拦截, 检查发表内容是否涉及违规
 */
@WebFilter(filterName = "publishFilter", urlPatterns = "/publish/**")
public class PublishFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//            servletRequest.get
    }
}

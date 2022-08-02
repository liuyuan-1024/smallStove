package com.bug.framework.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.bug.framework.constant.SystemConstant;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.security.models.entity.LoginUser;
import com.bug.framework.utils.JwtUtils;
import com.bug.framework.utils.RedisCache;
import com.bug.framework.utils.WebUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: JWT验证令牌过滤器（认证过滤器）我们会颁发两个token (access_token refresh_token),
 * 每次请求必须携带两个token 在此处实现了token的无感刷新和redis的刷新
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //从请求头中获access_token、refresh_token
        String access_token = request.getHeader(SystemConstant.ACCESS_TOKEN);
        String refresh_token = request.getHeader(SystemConstant.REFRESH_TOKEN);

        if (ObjectUtils.isEmpty(access_token) || ObjectUtils.isEmpty(refresh_token)) {
            // 白名单请求中无token, 如果请求的servlet路径以"/common"开头, 直接放行
            if (request.getServletPath().startsWith("/anonymous")) {
                filterChain.doFilter(request, response);
                return;
            }
            // token不合法，提示前端重新登录
            this.renderJson(response, ResultEnum.LOGIN_NOT);
            return;
        }

        try {
            // 校验token其合法性
            LoginUser loginUser = this.getLoginUserFromRedis(access_token);

            String username = loginUser.getUsername();

            if (JwtUtils.isLegal(access_token, username) || JwtUtils.isLegal(refresh_token, username)) {
                this.setAuthenticationToContext(loginUser);
                filterChain.doFilter(request, response);
            } else {
                // token不合法，提示前端重新登录
                this.renderJson(response, ResultEnum.AUTHENTICATION_EXPIRED);
            }
        } catch (NullPointerException e) {
            this.renderJson(response, ResultEnum.AUTHENTICATION_EXPIRED);
        }
    }

    private LoginUser getLoginUserFromRedis(String token) {
        // 解析token获取登录用户ID
        Object loginUserId = JwtUtils.parseToken(token).get(JwtUtils.LOGIN_USER_ID);
        //获取Redis中对应的key，然后获取redis中的LoginUser对象信息
        String redisJwtKey = redisCache.getLoginUserKey(Long.valueOf(String.valueOf(loginUserId)));
        // 返回Redis中存储的用户信息
        return JSON.parseObject(JSON.toJSONString(redisCache.get(redisJwtKey)), LoginUser.class);
    }

    private void setAuthenticationToContext(LoginUser loginUser) {
        //将此登录用户设置为已认证状态（当然，loginUser我们知道是已认证状态）
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        //将loginUser存入SecurityContext中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @SuppressWarnings("unchecked")
    private void renderJson(HttpServletResponse response, ResultEnum resultEnum) throws IOException {
        SerializeConfig config = new SerializeConfig();
        config.configEnumAsJavaBean(ResultEnum.class);
        WebUtils.renderJson(response, JSON.toJSONString(resultEnum, config));
    }

}

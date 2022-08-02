package com.bug.framework.security.config;

import com.bug.framework.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: SpringSecurity的配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // knife4jAPI文档的静态资源等路径
                .antMatchers("/swagger/**", "/swagger-ui.html", "/webjars/**", "/v2/**", "/v2/api-docs-ext/**", "/swagger-resources/**", "/doc.html")
                // WebSocket的连接路径
                .antMatchers("/websocket/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //允许跨域请求
        http.cors();

        //关闭跨域访问防护
        http.csrf().disable();

        //设置不创建HttpSession：基于token的认证，不需要从session中获取SecurityConfig，
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 限制登录用户数量, 同一账号只能一个用户使用
        http.sessionManagement().maximumSessions(1);

        //请求授权
        http.authorizeRequests().antMatchers("/anonymous/**").permitAll()
                //允许请求匿名访问
                .antMatchers("/", "/login").anonymous()
                //上面授权过的请求除外，其余请求全部需要被认证
                .anyRequest().authenticated();

        // 取消security的默认注销方式
        http.logout().disable();

        // 配置jwt登录认证过滤器
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置自定义异常处理器
        http.exceptionHandling()
                //认证异常
                .authenticationEntryPoint(authenticationEntryPoint)
                //授权异常
                .accessDeniedHandler(accessDeniedHandler);
    }
}

package com.bug.framework.service.impl;

import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.security.models.entity.LoginUser;
import com.bug.framework.security.models.vo.LoginUserVo;
import com.bug.framework.service.SysLoginService;
import com.bug.framework.service.TokenService;
import com.bug.framework.utils.BeanCopyUtils;
import com.bug.framework.utils.RedisCache;
import com.bug.framework.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @Author: BugOS-ly
 * @Date: 2022/7/15 21:19
 * @Description: 用户登录业务逻辑实现类
 */
@Service
public class SysLoginServiceImpl implements SysLoginService {
    @Resource
    private RedisCache redisCache;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private TokenService tokenService;


    @Override
    public Result<?> login(String username, String password) {
        //用户认证操作，会调用自定义登录逻辑，authentication不为null即认证成功
        Authentication authentication
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        //若认证没通过，给出对应的提示
        if (ObjectUtils.isEmpty(authentication)) {
            return new ResultBuilder().error(ResultEnum.LOGIN_FAIL);
        }

        // 这就是已认证的用户的信息
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 将LoginUser对象存入redis中
        redisCache.set(redisCache.getLoginUserKey(loginUser.getId()), loginUser, redisCache.getExpirationTime());

        // 生成token
        loginUser.setToken(tokenService.issueToken(loginUser).getData());

        LoginUserVo loginUserVo = BeanCopyUtils.copySingleBean(loginUser, LoginUserVo.class);

        // 将access_token、refresh_token发送给前端
        return new ResultBuilder().success(ResultEnum.LOGIN_SUCCESS, loginUserVo);
    }

    @Override
    public Result<?> logout() {
        //删除redis中的用户信息缓存
        redisCache.delete(redisCache.getLoginUserKey(SecurityUtils.getUserId()));

        // todo 将token加入token黑名单，token黑名单存储在redis中，并且黑名单过期时间和refresh_token过期时间一致

        return new ResultBuilder().success("注销登录成功");
    }

    @Override
    public Result<Boolean> isLogin(Long userId) {
        Object user = redisCache.get(redisCache.getLoginUserKey(userId));
        if (ObjectUtils.isEmpty(user)){
            return new ResultBuilder().success("用户未登录", false);
        }else {
            return new ResultBuilder().success("用户已登录", true);
        }
    }

}

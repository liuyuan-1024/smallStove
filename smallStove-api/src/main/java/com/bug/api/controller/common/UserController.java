package com.bug.api.controller.common;


import com.bug.framework.models.Result;
import com.bug.framework.security.domain.vo.LoginUserVo;
import com.bug.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "用户接口")
@RestController
public class UserController {

    @Resource
    private SysUserService userService;

    @ApiOperation(value = "用户信息", notes = "查询登录用户信息")
    @GetMapping("/user-info/{userId}")
    public Result<LoginUserVo> getUserInfo(@PathVariable("userId") Long userId) {
        return userService.getUserInfo(userId);
    }

    @ApiOperation(value = "修改用户信息", notes = "修改登录用户信息")
    @PutMapping("/user-info")
    public Result<?> updateUserInfo(@RequestBody LoginUserVo loginUserVo) {
        return userService.updateUserInfo(loginUserVo);
    }

}

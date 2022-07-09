package com.bug.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.framework.models.Result;
import com.bug.framework.security.domain.vo.LoginUserVo;
import com.bug.system.domain.entity.SysUser;

/**
 * @author 源
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service
 * @createDate 2022-05-31 16:48:26
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserByUsername(String username);

    Result<?> register(SysUser user);

    /**
     * 从Security获取LoginUser对象的信息
     */
    Result<LoginUserVo> getUserInfo( Long userId);

    /**
     * 修改用户信息
     */
    Result<?> updateUserInfo(LoginUserVo loginUserVo);
}

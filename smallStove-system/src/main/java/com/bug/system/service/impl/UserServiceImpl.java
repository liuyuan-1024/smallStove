package com.bug.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.framework.constant.SystemConstant;
import com.bug.framework.exception.SystemException;
import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.models.ResultEnum;
import com.bug.framework.security.domain.entity.LoginUser;
import com.bug.framework.security.domain.vo.LoginUserVo;
import com.bug.framework.utils.BeanCopyUtils;
import com.bug.framework.utils.SecurityUtils;
import com.bug.system.domain.entity.SysUser;
import com.bug.system.mapper.UserMapper;
import com.bug.system.service.SysUserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author 源
 * @description 针对表【sys_user(用户信息表)】的数据库操作Service实现
 * @createDate 2022-05-31 16:48:26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser>
        implements SysUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUser::getUsername, username);
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    @Override
    public Result<?> register(SysUser user) {
        // 加密密码
        String encode = SecurityUtils.encryptPassword(user.getPassword());
        user.setPassword(encode);
        // 将用户保存到数据库
        boolean save = this.save(user);
        // 设置用户的角色
        int i = userMapper.setRoleToUser(user.getId(), SystemConstant.COMMON_ROLE_ID);

        if (!save || i <= 0) {
            throw new SystemException(ResultEnum.REGISTER_FAIL);
        }

        return new ResultBuilder().success(ResultEnum.REGISTER_SUCCESS);
    }

    @Override
    public Result<LoginUserVo> getUserInfo(Long userId) {
        LoginUser loginUser = SecurityUtils.getLoginUser();

        if (ObjectUtils.isEmpty(loginUser)) {
            return new ResultBuilder().success(ResultEnum.USER_INFO_FAIL);
        }

        if (loginUser.getId().equals(userId)) {
            // 查询本人信息
            LoginUserVo loginUserVo = BeanCopyUtils.copySingleBean(loginUser, LoginUserVo.class);
            return new ResultBuilder().success(ResultEnum.USER_INFO_SUCCESS, loginUserVo);
        } else {
            SysUser user = userMapper.selectById(userId);
            // 数据脱敏
            LoginUserVo safetyUser = new LoginUserVo();
            safetyUser.setId(user.getId());
            safetyUser.setNickName(user.getUsername());
            safetyUser.setAvatar(user.getAvatar());
            return new ResultBuilder().success(ResultEnum.USER_INFO_SUCCESS, safetyUser);
        }
    }

    @Override
    public Result<?> updateUserInfo(LoginUserVo loginUserVo) {

        SysUser user = new SysUser();
        user.setId(SecurityUtils.getUserId());
        user.setNickname(loginUserVo.getNickName());
        user.setSex(loginUserVo.getSex());
        user.setAvatar(loginUserVo.getAvatar());
        user.setUpdateTime(new Date());

        if (userMapper.updateById(user) <= 0) {
            return new ResultBuilder().success(ResultEnum.USER_INFO_UPDATE_FAIL);
        }

        return new ResultBuilder().success(ResultEnum.USER_INFO_UPDATE_SUCCESS);
    }
}





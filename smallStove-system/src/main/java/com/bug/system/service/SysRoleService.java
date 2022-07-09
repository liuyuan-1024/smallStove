package com.bug.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.system.domain.entity.SysRole;

import java.util.List;

/**
 * @author 源
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service
 * @createDate 2022-05-31 18:44:14
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRoleByUserId(Long userId);

}

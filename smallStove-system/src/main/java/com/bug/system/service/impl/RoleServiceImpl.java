package com.bug.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.system.domain.entity.SysRole;
import com.bug.system.mapper.RoleMapper;
import com.bug.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 源
 * @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
 * @createDate 2022-05-31 18:44:14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole>
        implements SysRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<SysRole> getRoleByUserId(Long userId) {
        return roleMapper.selectAllRoleByUserId(userId);
    }

}





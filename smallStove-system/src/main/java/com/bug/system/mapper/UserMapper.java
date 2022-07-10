package com.bug.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bug.system.domain.entity.SysUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author 源
 * @description 针对表【sys_user(用户信息表)】的数据库操作Mapper
 * @createDate 2022-05-31 16:48:26
 * @Entity com.bug.system.domain.entity.SysUser
 */
public interface UserMapper extends BaseMapper<SysUser> {

    @Insert("insert into sys_user_role values (${userId},${roleId})")
    int setRoleToUser(@Param("userId") Long userId, @Param("roleId") Long roleId);
}





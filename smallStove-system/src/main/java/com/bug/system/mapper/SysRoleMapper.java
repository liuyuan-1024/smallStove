package com.bug.system.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.bug.system.domain.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 源
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2022-05-31 18:44:14
* @Entity com.bug.system.domain.entity.SysRole
*/
public interface SysRoleMapper extends BaseMapper<SysRole> {

    List<SysRole> selectAllRoleByUserId(@Param("userId") Long userId);
}





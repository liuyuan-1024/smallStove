package com.bug.system.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bug.system.domain.entity.SysMenu;

import java.util.List;

/**
 * @author 源
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
 * @createDate 2022-06-09 12:13:31
 * @Entity com.bug.system.domain.entity.SysMenu
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> selectAll();

    List<SysMenu> selectAllByRoleId(@Param("roleId")Long roleId);

    List<SysMenu> selectAllByUserId(@Param("userId") Long userId);

}





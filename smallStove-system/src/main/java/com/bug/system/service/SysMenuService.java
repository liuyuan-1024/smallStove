package com.bug.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bug.framework.models.Result;
import com.bug.system.domain.entity.SysMenu;
import com.bug.system.domain.vo.MenuVo;

import java.util.List;

/**
 * @author 源
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service
 * @createDate 2022-06-09 12:13:31
 */
public interface SysMenuService extends IService<SysMenu> {

    Result<List<MenuVo>> getMenu();
}

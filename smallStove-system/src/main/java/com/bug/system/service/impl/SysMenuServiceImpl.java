package com.bug.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bug.framework.models.Result;
import com.bug.framework.models.ResultBuilder;
import com.bug.framework.utils.BeanCopyUtils;
import com.bug.framework.utils.SecurityUtils;
import com.bug.system.domain.entity.SysMenu;
import com.bug.system.domain.vo.MenuVo;
import com.bug.system.mapper.SysMenuMapper;
import com.bug.system.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 源
 * @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
 * @createDate 2022-06-09 12:13:31
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
        implements SysMenuService {

    @Resource
    private SysMenuMapper menuMapper;

    @Override
    public Result<List<MenuVo>> getMenu() {
        List<SysMenu> menuList = menuMapper.selectAllByUserId(SecurityUtils.getUserId());
        List<MenuVo> menuVoList = BeanCopyUtils.copyAllBean(menuList, MenuVo.class);
        return new ResultBuilder().success("获取菜单成功", menuVoList);
    }
}





package com.bug.api.controller.anonymous;

import com.bug.framework.models.Result;
import com.bug.system.domain.vo.MenuVo;
import com.bug.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "菜单接口")
@RestController
@RequestMapping("/anonymous")
public class MenuController {

    @Resource
    private SysMenuService menuService;

    @ApiOperation(value = "菜单列表", notes = "查询所有菜单项")
    @GetMapping("/menu-list")
    public Result<List<MenuVo>> getMenu() {
        return menuService.getMenu();
    }

}

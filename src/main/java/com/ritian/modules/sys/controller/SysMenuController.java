package com.ritian.modules.sys.controller;

import com.ritian.common.bean.R;
import com.ritian.common.controller.BaseController;
import com.ritian.modules.sys.entity.SysMenu;
import com.ritian.modules.sys.service.ShiroService;
import com.ritian.modules.sys.service.SysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ritian.Zhang
 * @date 2019/01/04
 **/
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private ShiroService shiroService;


    /**
     * 导航菜单
     */
    @GetMapping("/nav")
    public R nav() {
        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUser());
        Set<String> permissions = shiroService.getUserPermissions(getUserId());
        return R.ok().put("menuList", menuList).put("permissions", permissions);
    }
}

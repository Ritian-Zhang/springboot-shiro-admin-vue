package com.ritian.modules.sys.controller;

import com.ritian.common.annotation.Log;
import com.ritian.common.bean.R;
import com.ritian.common.controller.BaseController;
import com.ritian.modules.sys.entity.SysRole;
import com.ritian.modules.sys.service.SysRoleMenuService;
import com.ritian.modules.sys.service.SysRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2019/01/09
 **/
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends BaseController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params){
       return  R.ok().put("page",sysRoleService.listByPage(params));
    }

    /**
     * 保存角色
     */
    @PostMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRole role){
        sysRoleService.save(role);
        return R.ok();
    }

    /**
     * 角色列表
     */
    @GetMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select(){
        List<SysRole> list = sysRoleService.list();
        return R.ok().put("list", list);
    }

    /**
     * 角色信息
     */
    @GetMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId){
        SysRole role = sysRoleService.getById(roleId);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        return R.ok().put("role", role);
    }

    /**
     * 修改角色
     */
    @Log("修改角色")
    @PostMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRole role){
        sysRoleService.updateById(role);
        return R.ok();
    }

    /**
     * 删除角色
     */
    @Log("删除角色")
    @PostMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteBatch(roleIds);
        return R.ok();
    }
}

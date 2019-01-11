package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.common.annotation.Log;
import com.ritian.common.util.Constant;
import com.ritian.modules.sys.entity.SysMenu;
import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.mapper.SysMenuMapper;
import com.ritian.modules.sys.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ritian.Zhang
 * @date 2018/12/29
 **/
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        return null;
    }

    @Override
    public List<SysMenu> queryListParentId(Long parentId) {
        return this.list(new QueryWrapper<SysMenu>().eq("parent_id", parentId).orderByAsc("order_num"));
    }

    @Override
    public List<SysMenu> queryNotButtonList() {
        return null;
    }

    @Override
    public List<SysMenu> getUserMenuList(SysUser sysUser) {
        if (Constant.SUPER_ADMIN.equals(sysUser.getUsername())) {
              return getAllMenuList();
        }
        return null;
    }

    @Override
    public void delete(Long menuId) {

    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList() {
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0L);
        //递归获取子菜单
        getMenuTreeList(menuList);
        return menuList;
    }

    /**
     * 递归 获取所有子菜单
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList) {
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();
        menuList.forEach(sysMenu -> {
            //目录
            if(sysMenu.getType() == Constant.MenuType.CATALOG.getValue()){
                sysMenu.setList(getMenuTreeList(queryListParentId(sysMenu.getMenuId())));
            }
            subMenuList.add(sysMenu);
        });
        return subMenuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIds) {
        List<SysMenu> subMenuList = new ArrayList<SysMenu>();

        menuList.forEach(sysMenu -> {
            //目录
            if(sysMenu.getType() == Constant.MenuType.CATALOG.getValue()){
               sysMenu.setList(getMenuTreeList(queryListParentId(sysMenu.getMenuId()),menuIds));
            }
            subMenuList.add(sysMenu);
        });
        return subMenuList;

    }
}

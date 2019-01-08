package com.ritian.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ritian.modules.sys.entity.SysMenu;
import com.ritian.modules.sys.entity.SysUser;

import java.util.List;

/**
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
public interface SysMenuService extends IService<SysMenu> {
    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenu> getUserMenuList(SysUser sysUser);

    /**
     * 删除
     */
    void delete(Long menuId);

}

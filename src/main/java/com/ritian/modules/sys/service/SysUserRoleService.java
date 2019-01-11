package com.ritian.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ritian.modules.sys.entity.SysUserRole;

import java.util.List;


/**
 * 用户与角色对应关系
 * @author ritian.Zhang
 * @date 2018/12/28
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    /**
     * 根据用户ID数组，批量删除
     */
    int deleteBatchByUserIds(Long[] userIds);
}

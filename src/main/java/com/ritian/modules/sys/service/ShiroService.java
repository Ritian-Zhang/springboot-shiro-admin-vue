package com.ritian.modules.sys.service;

import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.entity.SysUserToken;

import java.util.Set;

/**
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(SysUser sysUser);

    SysUserToken queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUser queryUser(Long userId);
}

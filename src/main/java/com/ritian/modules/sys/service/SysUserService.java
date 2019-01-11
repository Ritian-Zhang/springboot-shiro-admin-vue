package com.ritian.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ritian.common.util.PageUtil;
import com.ritian.modules.sys.entity.SysUser;

import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名，查询系统用户
     */
    SysUser queryByUsername(String username);

    /**
     * 分页
     */
    PageUtil listByPage(Map<String, Object> params);

    /**
     * 删除用户
     */
    void deleteBatch(Long[] userIds);

    /**
     * 修改用户
     */
    void update(SysUser user);

}

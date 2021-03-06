package com.ritian.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ritian.modules.sys.entity.SysUserRole;

import java.util.List;

/**
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);


    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(Long[] roleIds);

    int deleteBatchByUserIds(Long[] userIds);
}

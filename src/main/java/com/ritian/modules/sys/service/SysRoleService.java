package com.ritian.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ritian.common.util.PageUtil;
import com.ritian.modules.sys.entity.SysRole;

import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
public interface SysRoleService extends IService<SysRole> {

     PageUtil listByPage(Map<String, Object> params);

     void deleteBatch(Long[] roleIds);

}

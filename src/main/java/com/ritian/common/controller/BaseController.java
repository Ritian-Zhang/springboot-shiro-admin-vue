package com.ritian.common.controller;

import com.ritian.modules.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * web 层通用数据处理
 *
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
public class BaseController{

    protected SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    protected Long getUserId() {
        return getUser().getId();
    }
}

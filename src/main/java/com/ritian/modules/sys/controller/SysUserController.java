package com.ritian.modules.sys.controller;

import com.baomidou.mybatisplus.extension.api.ApiController;
import com.ritian.common.bean.R;
import com.ritian.common.controller.BaseController;
import com.ritian.common.util.Constant;
import com.ritian.common.util.PageUtil;
import com.ritian.modules.sys.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 所有用户列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params){
        //只有超级管理员，才能查看所有管理员列表
        PageUtil page = sysUserService.listByPage(params);
        return R.ok().put("page",page);
    }

}

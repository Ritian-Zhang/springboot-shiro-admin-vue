package com.ritian.modules.sys.controller;

import com.ritian.common.bean.R;
import com.ritian.common.controller.BaseController;
import com.ritian.common.util.PageUtil;
import com.ritian.modules.sys.service.SysLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统操作日志
 *
 * @author ritian.Zhang
 * @date 2019/01/11
 **/
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {

    @Resource
    private SysLogService sysLogService;

    /**
     * 日志列表
     */
    @GetMapping("/list")
    @RequiresPermissions("sys:log:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtil page = sysLogService.listByPage(params);
        return R.ok().put("page", page);
    }

}

package com.ritian.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ritian.common.util.PageUtil;
import com.ritian.modules.sys.entity.SysLog;

import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2019/01/11
 **/
public interface SysLogService extends IService<SysLog> {

    /**
     * 分页
     */
    PageUtil listByPage(Map<String, Object> params);

}

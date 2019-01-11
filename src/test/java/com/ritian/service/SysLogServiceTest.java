package com.ritian.service;

import com.ritian.ApplicationTests;
import com.ritian.modules.sys.entity.SysLog;
import com.ritian.modules.sys.service.SysLogService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author ritian.Zhang
 * @date 2019/01/11
 **/
public class SysLogServiceTest extends ApplicationTests {
    @Resource
    private SysLogService sysLogService;

    @Test
    public void add(){
        SysLog t = SysLog.builder().username("admin").time(10L).build();

        sysLogService.save(t);

        System.out.println(t.getId());
    }
}

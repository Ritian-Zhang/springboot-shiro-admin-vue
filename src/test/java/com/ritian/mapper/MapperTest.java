package com.ritian.mapper;

import com.ritian.ApplicationTests;
import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.mapper.SysUserMapper;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author ritian.Zhang
 * @date 2018/12/27
 **/
public class MapperTest extends ApplicationTests {

    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void test() {
        double a = 1000000 * 0.0049 * Math.pow((1 + 0.0049), 360);
        double b = Math.pow((1 + 0.0049), 360) - 1;
        System.out.println(a / b);
    }

    @Test
    public void testAdd() {
        sysUserMapper.insert(SysUser.builder().nickname("admin").build());
    }
}

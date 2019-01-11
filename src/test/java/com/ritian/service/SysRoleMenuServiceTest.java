package com.ritian.service;

import com.ritian.ApplicationTests;
import com.ritian.modules.sys.entity.SysRole;
import com.ritian.modules.sys.mapper.SysRoleMapper;
import com.ritian.modules.sys.service.SysRoleMenuService;
import com.ritian.modules.sys.service.SysRoleService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ritian.Zhang
 * @date 2019/01/09
 **/
public class SysRoleMenuServiceTest extends ApplicationTests {
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Test
    public void save() {
        List<Long> list = new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);
        sysRoleMenuService.saveOrUpdate(1L, list);
    }

    @Test
    public void saveRole() {
        SysRole t = SysRole.builder().roleName("aa").createTime(new Date()).build();
//       this.sysRoleMapper.insert(t);
        sysRoleService.save(t);

    }
}

package com.ritian.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ritian.ApplicationTests;
import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.service.SysMenuService;
import com.ritian.modules.sys.service.SysUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2018/12/29
 **/
public class ServiceTest extends ApplicationTests {
    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysMenuService sysMenuService;
    
    @Test
    public void add(){
        //sysUserService.save(SysUser.builder().nickname("js").username("tony").password("123456").build());
    }
    @Test
    public void list(){
        sysMenuService.queryListParentId(1L);
    }

    @Test
    public void page(){
        Page<SysUser> page = new Page<>(1,2);
        String username = "";
        QueryWrapper<SysUser> wrapper =  new QueryWrapper<>();
        wrapper.eq(false,"username",username);
        sysUserService.page(page, wrapper);
    }
    @Test
    public void listByPage(){
        Map<String,Object>  map  = new HashMap<>();
        map.put("username","a");
        sysUserService.listByPage(map);
    }

    @Test
    public void delete(){
        sysUserService.deleteBatch(new Long[]{6L});
    }

    @Test
    public void sha(){
        String salt = RandomStringUtils.randomAlphabetic(20);
        System.out.println(salt);
        String s = new Sha256Hash("123456", salt).toHex();
        System.out.println(s);
    }
}

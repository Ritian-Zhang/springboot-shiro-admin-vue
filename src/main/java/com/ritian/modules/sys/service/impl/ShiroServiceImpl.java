package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ritian.common.util.Constant;
import com.ritian.common.util.StringUtils;
import com.ritian.modules.sys.entity.SysMenu;
import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.entity.SysUserToken;
import com.ritian.modules.sys.mapper.SysMenuMapper;
import com.ritian.modules.sys.mapper.SysUserMapper;
import com.ritian.modules.sys.mapper.SysUserTokenMapper;
import com.ritian.modules.sys.service.ShiroService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ritian.Zhang
 * @date 2018/12/29
 */
@Service
public class ShiroServiceImpl implements ShiroService {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysUserTokenMapper sysUserTokenMapper;

    @Override
    public Set<String> getUserPermissions(long userId) {
        SysUser sysUser = this.queryUser(userId);
        return getUserPermissions(sysUser);
    }

    @Override
    public Set<String> getUserPermissions(SysUser sysUser) {
        List<String> permsList = new ArrayList<>();
        //系统管理员，拥有最高权限
        if(sysUser.getUsername().equals(Constant.SUPER_ADMIN)){
            List<SysMenu> menuList = sysMenuMapper.selectList(null);
            menuList.forEach(sysMenu -> {
                permsList.add(sysMenu.getPerms());
            });
        }else{
            //TODO

        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        permsList.forEach(perms ->{
            if(StringUtils.isNotBlank(perms)){
                permsSet.addAll(Arrays.asList(perms.trim().split(",")));
            }
        });
     /*   for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }*/
        return permsSet;
    }

    @Override
    public SysUserToken queryByToken(String token) {
       return sysUserTokenMapper.selectOne(new QueryWrapper<SysUserToken>().eq("token",token));
    }

    @Override
    public SysUser queryUser(Long userId) {
        return sysUserMapper.selectById(userId);
    }
}

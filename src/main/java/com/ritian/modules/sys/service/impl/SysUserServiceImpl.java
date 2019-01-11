package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.common.util.PageUtil;
import com.ritian.common.util.Query;
import com.ritian.common.util.StringUtils;
import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.mapper.SysUserMapper;
import com.ritian.modules.sys.service.SysUserRoleService;
import com.ritian.modules.sys.service.SysUserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2018/12/29
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser queryByUsername(String username) {
        return this.getOne(new QueryWrapper<SysUser>().eq("username", username), true);
    }

    @Override
    public PageUtil listByPage(Map<String, Object> params) {
        String username = (String) params.get("username");
        IPage<SysUser> page = this.page(
                new Query<SysUser>(params).getPage(),
                new QueryWrapper<SysUser>().
                        like(StringUtils.isNotBlank(username), "username", username));
        return new PageUtil(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] userIds) {
        //删除用户
        this.removeByIds(Arrays.asList(userIds));
        //删除用户与角色对应关系
        sysUserRoleService.deleteBatchByUserIds(userIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysUser user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphabetic(20);
        user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
        user.setSalt(salt);
        super.save(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(),user.getRoleIdList());
        return true;
    }

    @Override
    public void update(SysUser user) {
        if(StringUtils.isBlank(user.getPassword())){
            user.setPassword(null);
        }else{
            user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
        }
        this.updateById(user);

        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getId(),user.getRoleIdList());
    }
}

package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.common.util.PageUtil;
import com.ritian.common.util.Query;
import com.ritian.common.util.StringUtils;
import com.ritian.modules.sys.entity.SysRole;
import com.ritian.modules.sys.mapper.SysRoleMapper;
import com.ritian.modules.sys.service.SysRoleMenuService;
import com.ritian.modules.sys.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysRole entity) {
        entity.setCreateTime(new Date());
        super.save(entity);
        sysRoleMenuService.saveOrUpdate(entity.getRoleId(), entity.getMenuIdList());
        return true;
    }

    @Override
    public PageUtil listByPage(Map<String, Object> params) {
        String roleName = (String) params.get("roleName");
        IPage<SysRole> page = this.page(
                new Query<SysRole>(params).getPage(),
                new QueryWrapper<SysRole>().like(StringUtils.isNotBlank(roleName), "role_name", roleName));
        return new PageUtil(page);
    }

    @Override
    public void deleteBatch(Long[] roleIds) {
        //删除角色
        this.removeByIds(Arrays.asList(roleIds));

        //删除角色与菜单关联
        sysRoleMenuService.deleteBatch(roleIds);

        //删除角色与用户关联
    }

}

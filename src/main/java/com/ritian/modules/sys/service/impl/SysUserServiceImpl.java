package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.common.util.PageUtil;
import com.ritian.common.util.Query;
import com.ritian.common.util.StringUtils;
import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.mapper.SysUserMapper;
import com.ritian.modules.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author ritian.Zhang
 * @date 2018/12/29
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

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
        return  new PageUtil(page);
    }
}

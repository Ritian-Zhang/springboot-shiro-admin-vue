package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.modules.sys.entity.SysRole;
import com.ritian.modules.sys.mapper.SysRoleMapper;
import com.ritian.modules.sys.service.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    @Override
    public boolean save(SysRole entity) {
        return super.save(entity);
    }
}

package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.common.util.MapUtils;
import com.ritian.modules.sys.entity.SysUserRole;
import com.ritian.modules.sys.mapper.SysUserRoleMapper;
import com.ritian.modules.sys.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户与角色对应关系
 * @author ritian.Zhang
 * @date 2019/01/09
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

	@Override
	public void saveOrUpdate(Long userId, List<Long> roleIdList) {
		//先删除用户与角色关系
		this.removeByMap(new MapUtils().put("user_id", userId));

		if(roleIdList == null || roleIdList.size() == 0){
			return;
		}
		//保存用户与角色关系
		List<SysUserRole> list  =  new ArrayList<>(roleIdList.size());
        for(Long roleId : roleIdList){
        	list.add(SysUserRole.builder().roleId(roleId).userId(userId).build());
		}
		this.saveBatch(list);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return baseMapper.queryRoleIdList(userId);
	}

	@Override
	public int deleteBatch(Long[] roleIds){
		return baseMapper.deleteBatch(roleIds);
	}

	@Override
	public int deleteBatchByUserIds(Long[] userIds) {
		return this.baseMapper.deleteBatchByUserIds(userIds);
	}


}

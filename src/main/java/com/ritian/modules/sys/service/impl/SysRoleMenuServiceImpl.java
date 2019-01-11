package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.modules.sys.entity.SysRoleMenu;
import com.ritian.modules.sys.mapper.SysRoleMenuMapper;
import com.ritian.modules.sys.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 角色与菜单对应关系
 * @author ritian.Zhang
 * @date 2019/01/07
 */
@Service()
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		deleteBatch(new Long[]{roleId});

		if(menuIdList.size() == 0){
			return ;
		}
		//保存角色与菜单关系
		List<SysRoleMenu> list = new ArrayList<>(menuIdList.size());
		menuIdList.forEach(menuId ->{
			list.add(SysRoleMenu.builder().menuId(menuId).roleId(roleId).build());
		});
		this.saveBatch(list);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return this.baseMapper.queryMenuIdList(roleId);
	}

	@Override
	public int deleteBatch(Long[] roleIds) {
		return baseMapper.deleteBatch(roleIds);
	}


}

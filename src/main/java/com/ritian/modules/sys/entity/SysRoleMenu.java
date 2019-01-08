package com.ritian.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色与菜单对应关系
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role_menu")
public class SysRoleMenu {

    private Long id;

    private Long roleId;

    private Long menuId;



}

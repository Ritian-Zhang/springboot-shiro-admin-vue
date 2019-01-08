package com.ritian.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户与角色对应关系
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_role")
public class SysUserRole {

    private Long id;

    private Long userId;

    private Long roleId;
}

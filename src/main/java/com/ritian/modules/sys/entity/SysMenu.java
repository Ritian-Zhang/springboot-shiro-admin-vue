package com.ritian.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ritian.Zhang
 * @date 2019/01/04
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
public class SysMenu {
    /** 菜单ID */
    private Long menuId;

    /** 菜单名称 */
    private String name;

    /** 菜单URL */
    private String url;

    /** 父菜单ID */
    private Long parentId;

    /** 显示顺序 */
    private Integer orderNum;

    /** 类型:0目录,1菜单,2按钮 */
    private Integer type;

    /** 权限字符串 */
    private String perms;

    /** 菜单图标 */
    private String icon;

    @TableField(exist=false)
    private List<?> list;

}

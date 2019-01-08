package com.ritian.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_token")
public class SysUserToken {
    /*用户ID*/
    @TableId(type = IdType.INPUT)
    private Long userId;
    //token
    private String token;
    //过期时间
    private Date expireTime;
    //更新时间
    private Date updateTime;
}

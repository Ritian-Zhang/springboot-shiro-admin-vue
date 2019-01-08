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
 * 验证码
 * @author ritian.Zhang
 * @date 2019/01/03
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_captcha")
public class SysCaptcha {

    @TableId(type = IdType.INPUT)
    private String uuid;

    private String code;

    private Date expireTime;
}

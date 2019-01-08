package com.ritian.modules.sys.dto.sys;

import lombok.Data;

/**
 * @author ritian.Zhang
 * @date 2019/01/03
 **/
@Data
public class UserLoginDTO {

    private String username;

    private String password;

    private String uuid;

    private String captcha;
}

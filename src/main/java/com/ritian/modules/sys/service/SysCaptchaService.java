package com.ritian.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ritian.modules.sys.entity.SysCaptcha;

import java.awt.image.BufferedImage;

/**
 * @author ritian.Zhang
 * @date 2019/01/02
 **/
public interface SysCaptchaService extends IService<SysCaptcha> {
    /**
     * 获取图片验证码
     */
    BufferedImage getCaptcha(String uuid);

    /**
     * 验证码效验
     * @param uuid  uuid
     * @param code  验证码
     * @return  true：成功  false：失败
     */
    boolean validate(String uuid, String code);
}

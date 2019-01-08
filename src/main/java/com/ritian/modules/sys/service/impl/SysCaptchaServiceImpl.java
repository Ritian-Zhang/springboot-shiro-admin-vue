package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.ritian.common.exception.RiTianException;
import com.ritian.common.util.DateUtils;
import com.ritian.common.util.StringUtils;
import com.ritian.modules.sys.entity.SysCaptcha;
import com.ritian.modules.sys.mapper.SysCaptchaMapper;
import com.ritian.modules.sys.service.SysCaptchaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @author ritian.Zhang
 * @date 2019/01/03
 **/
@Service
public class SysCaptchaServiceImpl extends ServiceImpl<SysCaptchaMapper, SysCaptcha> implements SysCaptchaService {

    @Resource
    private Producer producer;

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new RiTianException("uuid不能为空");
        }
        //生成文字验证码
        String code = producer.createText();
        this.save(SysCaptcha.builder()
                .uuid(uuid)
                .code(code)
                //5分钟后过期
                .expireTime(DateUtils.addDateMinutes(new Date(), 5))
                .build());
        return producer.createImage(code);
    }

    @Override
    public boolean validate(String uuid, String code) {
        SysCaptcha captcha = this.getOne(new QueryWrapper<SysCaptcha>().eq("uuid", uuid));
        if (captcha == null) {
            return false;
        }

        //删除验证码
        this.removeById(uuid);
        if (captcha.getCode().equalsIgnoreCase(code) && captcha.getExpireTime().getTime() >= System.currentTimeMillis()) {
            return true;
        }
        return false;
    }
}

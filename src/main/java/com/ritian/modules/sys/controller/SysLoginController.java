package com.ritian.modules.sys.controller;

import com.ritian.common.bean.R;
import com.ritian.common.controller.BaseController;
import com.ritian.modules.sys.dto.sys.UserLoginDTO;
import com.ritian.modules.sys.entity.SysUser;
import com.ritian.modules.sys.service.SysCaptchaService;
import com.ritian.modules.sys.service.SysUserService;
import com.ritian.modules.sys.service.SysUserTokenService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 登录验证
 *
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
@RestController
public class SysLoginController extends BaseController {

    private static String SALT = "ritian";

    @Resource
    private SysCaptchaService sysCaptchaService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysUserTokenService sysUserTokenService;

    /**
     * 验证码
     */
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 登录
     */
    @PostMapping("/sys/login")
    public R login(@RequestBody UserLoginDTO dto) {
        boolean captcha = sysCaptchaService.validate(dto.getUuid(), dto.getCaptcha());
        if (!captcha) {
            return R.error("验证码不正确");
        }
        SysUser user = sysUserService.queryByUsername(dto.getUsername());
        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(new Sha256Hash(dto.getPassword(), user.getSalt()).toHex())) {
            return R.error("账号或密码不正确");
        }
        //账号锁定
        if (user.getStatus() == 0) {
            return R.error("账号已被锁定,请联系管理员");
        }
        return sysUserTokenService.createToken(user.getId());
    }


    /**
     * 登出
     */
    /**
     * 退出
     */
    @PostMapping("/sys/logout")
    public R logout() {
        sysUserTokenService.logout(getUserId());
        return R.ok();
    }
}

package com.ritian.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ritian.common.bean.R;
import com.ritian.modules.sys.entity.SysUserToken;
import com.ritian.modules.sys.mapper.SysUserTokenMapper;
import com.ritian.modules.sys.oauth2.TokenGenerator;
import com.ritian.modules.sys.service.SysUserTokenService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
@Service
public class SysUserTokenServiceImpl extends ServiceImpl<SysUserTokenMapper, SysUserToken> implements SysUserTokenService {

    //12小时后过期
    private final static long EXPIRE = 3600 * 12;

    @Override
    public R createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserToken userToken = this.getById(userId);

        if (userToken == null) {
            userToken = SysUserToken.builder().userId(userId).token(token).updateTime(now).expireTime(expireTime).build();
            this.save(userToken);
        } else {
            userToken.setToken(token);
            userToken.setUpdateTime(now);
            userToken.setExpireTime(expireTime);
            this.updateById(userToken);
        }

        return R.ok().put("token", token).put("expire", EXPIRE);
    }

    @Override
    public void logout(long userId) {
        //修改token
        this.updateById(SysUserToken.builder().
                userId(userId).
                token(TokenGenerator.generateValue()).
                build());
    }
}

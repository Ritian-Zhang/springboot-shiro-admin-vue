package com.ritian.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ritian.common.bean.R;
import com.ritian.modules.sys.entity.SysUserToken;

/**
 * @author ritian.Zhang
 * @date 2019/01/07
 **/
public interface SysUserTokenService extends IService<SysUserToken> {

    /**
     * 生成token
     * @param userId  用户ID
     */
    R createToken(long userId);

    /**
     * 退出，修改token值
     * @param userId  用户ID
     */
    void logout(long userId);
}

package com.ritian.common.bean;

import lombok.AllArgsConstructor;

/**
 * 响应码枚举，参考HTTP状态码的语义
 *
 * @author ritian.Zhang
 * @date 2018/12/25
 **/
@AllArgsConstructor
public enum ResultCode {
    /**
     * 操作成功
     */
    SUCCESS(0, "success"),
    /**
     * 500
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    /**
     * 操作失败
     */
    FAIL(-100, "fail");


    public int code;

    public String msg;


}

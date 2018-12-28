package com.ritian.common.exception;

/**
 * 自定义异常
 * @author ritian.Zhang
 * @date 2018/12/26
 **/
public class RiTianException extends RuntimeException {

    public RiTianException() {

    }

    public RiTianException(String message) {
        super(message);
    }

    public RiTianException(String message, Throwable cause) {
        super(message, cause);
    }
}

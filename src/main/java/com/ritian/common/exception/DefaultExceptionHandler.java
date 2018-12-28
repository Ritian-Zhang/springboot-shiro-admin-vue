package com.ritian.common.exception;

import com.ritian.common.bean.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
@RestControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RiTianException.class)
    public R handleReqException(RiTianException e){
        log.error(e.getMessage(),e);
        return R.error(e.getMessage());
    }
    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R notFount(RuntimeException e)
    {
        log.error("运行时异常:", e);
        return R.error("运行时异常:" + e.getMessage());
    }
    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        return R.error("服务器错误，请联系管理员");
    }

    /**
     * 404 路径不存在
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public R handlerNoFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage());
        return R.error(404, "路径不存在，请检查路径是否正确");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handlerNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return R.error("不支持' " + e.getMethod() + "'请求");
    }
}

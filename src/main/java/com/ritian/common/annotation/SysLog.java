package com.ritian.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    String value() default "";
}

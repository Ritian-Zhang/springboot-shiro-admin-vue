package com.ritian.common.bean;


import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", ResultCode.SUCCESS.code);
        put("msg", "success");
    }

    public static R error() {
        return error(ResultCode.INTERNAL_SERVER_ERROR.code, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(ResultCode.INTERNAL_SERVER_ERROR.code, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

}

package com.ritian.modules.sys.controller;

import com.ritian.common.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ritian.Zhang
 * @date 2018/12/28
 **/
@RestController
@RequestMapping("/test")
@Api("测试接口")
public class TestController extends BaseController {

    @PostMapping("demo")
    @ApiOperation("测试")
    public String test(){
        return "test";
    }

}

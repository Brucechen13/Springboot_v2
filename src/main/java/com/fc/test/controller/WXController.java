package com.fc.test.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("UserController")
@Api(value = "微信接口")
public class WXController {
    @ApiOperation(value="测试",notes="测试")
    @GetMapping("/test")
    public String index() {
        return "test";
    }
}

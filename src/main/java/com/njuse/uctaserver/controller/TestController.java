package com.njuse.uctaserver.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "测试api")
    @GetMapping(value = "/isSuccess")
    public @ResponseBody  ResponseEntity<String> isSuccess(HttpServletRequest request) {
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

}
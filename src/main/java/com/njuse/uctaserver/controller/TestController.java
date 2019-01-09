package com.njuse.uctaserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {


    @RequestMapping(value = "/isSuccess", method = RequestMethod.GET)
    public @ResponseBody
    String isSuccess(HttpServletRequest request) {
        return "myfirstattempt";
    }

}
package com.njuse.uctaserver.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody
    String tryLogin(String code, HttpSession session){
        session.setAttribute("code",code);
        return code;
    }

    @RequestMapping(value = "/getCode",method = RequestMethod.GET)
    public @ResponseBody
    String getMyCode(HttpSession session){
        return session.getAttribute("code").toString();
    }

}

package com.njuse.uctaserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.njuse.uctaserver.config.MicroProgram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    MicroProgram microProgram;

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public @ResponseBody
    String tryLogin(HttpServletRequest request){
        String jscode = request.getParameter("code");
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> re = rt.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code",String.class,microProgram.getAppID(),microProgram.getAppSecret(),jscode);
        JSONObject jo = JSONObject.parseObject(re.getBody());
        HttpSession session = request.getSession();
        session.setAttribute("session_key",jo.get("session_key"));
        Map<String,String> resultmap = new HashMap<String,String>();
        resultmap.put("openid",jo.get("openid").toString());
        resultmap.put("sessionid",session.getId());
        return jo.get("openid").toString();
    }
}

package com.njuse.uctaserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.njuse.uctaserver.config.MicroProgram;
import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.service.UserService;
import com.njuse.uctaserver.until.MemberAttitude;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    final
    UserService userService;

    final
    MicroProgram microProgram;

    @Autowired
    public UserController(UserService userService, MicroProgram microProgram) {
        this.userService = userService;
        this.microProgram = microProgram;
    }

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

    @ApiOperation(value = "获取指定id用户信息")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<User> get(@PathVariable String id) {
        User activity = userService.get(id);
        HttpStatus resCode = HttpStatus.OK;
        if (activity == null)
            resCode = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(activity, resCode);
    }

    @ApiOperation(value = "评价用户点赞或点踩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "maCode", value = "态度0：like|1:thread", required = true, dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{id}/{maCode}")
    public @ResponseBody
    ResponseEntity<String> audit(@PathVariable String id, @RequestBody int maCode) {
        HttpStatus resCode = userService.likeOrThread(id, maCode);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

}

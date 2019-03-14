package com.njuse.uctaserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.njuse.uctaserver.config.MicroProgram;
import com.njuse.uctaserver.dto.UserDTO;
import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "User Controller")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final MicroProgram microProgram;

    @Autowired
    public UserController(UserService userService, MicroProgram microProgram) {
        this.userService = userService;
        this.microProgram = microProgram;
    }

    @PostMapping(value = "/login")
    public @ResponseBody
    Map tryLogin(@RequestBody Map<String,Object> paraments, HttpSession session){

        String idName = "openid";

        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> re = rt.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid={1}&secret={2}&js_code={3}&grant_type=authorization_code",String.class,microProgram.getAppID(),microProgram.getAppSecret(),paraments.get("jscode").toString());
        JSONObject jo = JSONObject.parseObject(re.getBody());
        String openId = jo.get(idName).toString();

        User user = userService.get(openId);
        if(user == null)
            user = new User();
        user.setId(openId);
        user.setNickName(paraments.get("nickName").toString());
        user.setGender(Integer.parseInt(paraments.get("gender").toString()));
        user.setAvatarUrl(paraments.get("avatarUrl").toString());
        user.setCity(paraments.get("city").toString());
        user.setProvince(paraments.get("province").toString());
        session.setAttribute(idName,openId);
        Map map = new HashMap<String,String>();
        map.put(idName,openId);
        map.put("session_id",session.getId());
        return map;
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
        User user = userService.get(id);
        HttpStatus resCode = user == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(user, resCode);
    }

    @ApiOperation(value = "评价用户点赞或点踩")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path"),
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
    ResponseEntity<String> likeOrThread(@PathVariable String id, @PathVariable int maCode) {
        HttpStatus resCode = userService.likeOrThread(id, maCode);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "添加用户标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "label", value = "string", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{id}/label/{label}")
    public @ResponseBody
    ResponseEntity<String> addLabel(@PathVariable String id, @PathVariable String label) {
        HttpStatus resCode = userService.addLabel(id, label);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }


    @ApiOperation(value = "创建用户信息")
    @ApiImplicitParam(name = "userDTO", value = "用户详情DTO类", required = true, dataType = "UserDTO", paramType = "body")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> createOrUpdate(@RequestBody UserDTO userDTO) {
        User user = userService.get(userDTO.getId());
        if(user == null)
            user = new User();
        BeanUtils.copyProperties(userDTO, user, "likeNum","treadNum", "labels");
        HttpStatus resCode = userService.addOrUpdate(user);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

}

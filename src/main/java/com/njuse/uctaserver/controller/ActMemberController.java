package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.service.ActMemberService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

@Api(tags = "Activity Member Controller")
@Controller
@RequestMapping("/activities")
public class ActMemberController {

    private final ActMemberService actMemberService;

    @Autowired
    public ActMemberController(ActMemberService actMemberService) {
        this.actMemberService = actMemberService;
    }

    @ApiOperation(value = "删除指定 actId 出游活动的指定 userId 成员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "actId", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "userId", value = "成员id", required = true, dataType = "String", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @DeleteMapping(value = "/{actId}/users/{userId}")
    public @ResponseBody
    ResponseEntity<String> delete(HttpSession session, @PathVariable String actId, @PathVariable String userId) {
        String ownerId = String.valueOf(session.getAttribute("openid"));
        HttpStatus resCode = actMemberService.deleteByOwner(actId, userId, ownerId);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取指定id出游活动的参与用户")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/{id}/users")
    public @ResponseBody
    ResponseEntity<List<User>> getUsers(@PathVariable String id) {
        List<User> users = actMemberService.getUsersPartInAct(id);
        HttpStatus resCode = users.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(users, resCode);
    }

    @ApiOperation(value = "指定 actId 出游活动的成员退出")
    @ApiImplicitParam(name = "actId", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @DeleteMapping(value = "/{actId}/user")
    public @ResponseBody
    ResponseEntity<String> memberExit(HttpSession session, @PathVariable String actId) {
        String userId = String.valueOf(session.getAttribute("openid"));
        HttpStatus resCode = actMemberService.deleteByUser(actId, userId);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }


}

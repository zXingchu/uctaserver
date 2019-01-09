package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.OrgComment;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Organization Comment Controller")
@Controller
@RequestMapping("/comment/org/")
public class OrgCommentController {


    @ApiOperation(value = "获取指定id活动的所有评论")
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @GetMapping(value = "/activity/{id}")
    public @ResponseBody
    ResponseEntity<List<OrgComment>> getAllByUserId(@PathVariable String id) {
        List<OrgComment> memberComment = new ArrayList<OrgComment>();
        return new ResponseEntity<List<OrgComment>>(memberComment, HttpStatus.OK);
    }

    @ApiOperation(value = "创建指定id活动的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "memberComment", value = "组织评论详情实体类", required = true, dataType = "OrgComment", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> create(@PathVariable String id, @RequestBody OrgComment memberComment) {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }


    @ApiOperation(value = "获取指定id活动的评分")
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @GetMapping(value = "/activity/{id}/score")
    public @ResponseBody
    ResponseEntity<Integer> getScore(@PathVariable String id) {
        Integer memberComment = new Integer(10);
        return new ResponseEntity<Integer>(memberComment, HttpStatus.OK);
    }

}

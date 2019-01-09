package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.Activity;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Activity Controller")
@Controller
@RequestMapping("/activities")
public class ActivityController {


    @ApiOperation(value = "获取当前所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "过滤条件", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @GetMapping(value = "")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAll(@RequestParam(value = "param1", required = true) String param1) {
        List<Activity> activities = new ArrayList<Activity>();
        return new ResponseEntity<List<Activity>>(activities, HttpStatus.OK);
    }

    @ApiOperation(value = "创建出游活动")
    @ApiImplicitParam(name = "activity", value = "出游活动详情实体类", required = true, dataType = "Activity", paramType = "body")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> create(@RequestBody Activity activity) {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @ApiOperation(value = "删除指定id出游活动")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<String> remove(@PathVariable String id) {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @ApiOperation(value = "更新指定id出游活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "activity", value = "出游活动详情实体类", required = true, dataType = "Activity", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<String> update(@PathVariable String id, @RequestBody Activity activity) {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @ApiOperation(value = "获取指定id出游活动")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<Activity> get(@PathVariable String id) {
        Activity activity = new Activity();
        return new ResponseEntity<Activity>(activity, HttpStatus.OK);
    }


    @ApiOperation(value = "获取当前用户参与的所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "param1", value = "过滤条件", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @GetMapping(value = "/user/{id}")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAllByUserId(@PathVariable String id, @RequestParam(value = "param1", required = true) String param1) {
        List<Activity> activities = new ArrayList<Activity>();
        return new ResponseEntity<List<Activity>>(activities, HttpStatus.OK);
    }

}

package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.service.ActivityService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "Activity Controller")
@Controller
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    ActivityService activityService;


    @ApiOperation(value = "获取当前所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "过滤条件", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @GetMapping(value = "")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAll(@RequestParam(value = "param1", required = false) String param1) {
        List<Activity> activities = activityService.getAll();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @ApiOperation(value = "创建出游活动")
    @ApiImplicitParam(name = "activity", value = "出游活动详情实体类", required = true, dataType = "Activity", paramType = "body")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> create(@RequestBody Activity activity) {
        HttpStatus resCode = activityService.add(activity);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "删除指定id出游活动")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @DeleteMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<String> delete(@PathVariable String id) {
        HttpStatus resCode = activityService.delete(id);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
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
        HttpStatus resCode = activityService.update(activity);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取指定id出游活动")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<Activity> get(@PathVariable String id) {
        Activity activity = activityService.get(id);
        HttpStatus resCode = HttpStatus.OK;
        if(activity == null)
            resCode = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(activity, resCode);
    }


    @ApiOperation(value = "获取当前用户参与的所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "param1", value = "过滤条件", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @GetMapping(value = "/user/{id}")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAllByUserId(@PathVariable String id, @RequestParam(value = "param1", required = true) String param1) {
        List<Activity> activities = activityService.getAll(id);
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

}

package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.dto.ActivityDTO;
import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.service.ActivityService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
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

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }


    @ApiOperation(value = "获取当前所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "过滤条件开始时间 startTime", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "过滤条件参与最多人数 number", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "过滤条件状态 status", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "place", value = "过滤条件活动地点 place", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @GetMapping(value = "")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAll(@RequestParam(value = "startTime", required = false) String startTime,
                                          @RequestParam(value = "number", required = false) String number,
                                          @RequestParam(value = "status", required = false) String status,
                                          @RequestParam(value = "place", required = false) String place) {
        List<Activity> activities = activityService.getAll();
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @ApiOperation(value = "创建出游活动")
    @ApiImplicitParam(name = "activityDTO", value = "出游活动详情实体类", required = true, dataType = "ActivityDTO", paramType = "body")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> create(@RequestBody ActivityDTO activityDTO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDTO, activity);
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
            @ApiImplicitParam(name = "activityDTO", value = "出游活动详情实体类", required = true, dataType = "ActivityDTO", paramType = "body")
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
    ResponseEntity<String> update(@PathVariable String id, @RequestBody ActivityDTO activityDTO) {
        HttpStatus resCode = activityService.update(activityDTO);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "审核指定id出游活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "res", value = "申请结果1：accept|-1:reject", required = true, dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{id}/{res}")
    public @ResponseBody
    ResponseEntity<String> audit(@PathVariable String id, @RequestBody int res) {
        HttpStatus resCode = activityService.audit(id, res);
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
        HttpStatus resCode = activity == null ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(activity, resCode);
    }


    @ApiOperation(value = "获取当前用户参与的所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "param1", value = "过滤条件", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/user/{id}")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAllByUserId(@PathVariable String id, @RequestParam(value = "param1", required = true) String param1) {
        List<Activity> activities = activityService.getAllByUserId(id);
        HttpStatus resCode = activities.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(activities, resCode);
    }

    @ApiOperation(value = "获取指定name的所有活动")
    @ApiImplicitParam(name = "name", value = "名称", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/name/{name}")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAllByActName(@PathVariable String name) {
        List<Activity> activities = activityService.getAllByName(name);
        HttpStatus resCode = activities.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(activities, resCode);
    }

    @ApiOperation(value = "获取所有审核中的活动")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping(value = "/auditing")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAllAuditing() {
        List<Activity> activities = activityService.getAllAuditing();
        HttpStatus resCode = activities.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(activities, resCode);
    }

}

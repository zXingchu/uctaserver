package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.EntryApplication;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@Api(tags = "Application Controller")
@Controller
@RequestMapping("/applications")
public class ApplyController {

    @ApiOperation(value = "申请参加出游活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "application", value = "申请参加详情实体类", required = true, dataType = "EntryApplication", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/activities/{id}")
    public @ResponseBody
    ResponseEntity<String> joinActivity(@PathVariable String id, @RequestBody EntryApplication application) {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @ApiOperation(value = "获取当前所有活动的所有申请")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @GetMapping(value = "/activities/{id}")
    public @ResponseBody
    ResponseEntity<List<EntryApplication>> getAll(@PathVariable String id) {
        List<EntryApplication> entryApplications = new ArrayList<EntryApplication>();
        return new ResponseEntity<List<EntryApplication>>(entryApplications, HttpStatus.OK);
    }

    @ApiOperation(value = "通过申请")
    @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @PostMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<String> permit(@PathVariable String id) {

        return new ResponseEntity<String>("", HttpStatus.OK);
    }

}

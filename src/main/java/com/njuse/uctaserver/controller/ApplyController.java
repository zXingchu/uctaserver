package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.EntryApplication;
import com.njuse.uctaserver.service.ApplyService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "Application Controller")
@Controller
@RequestMapping("/applications")
public class ApplyController {

    @Autowired
    ApplyService applyService;

    @ApiOperation(value = "申请参加出游活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "application", value = "申请参加详情实体类", required = true, dataType = "EntryApplication", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @PostMapping(value = "/activities/{id}")
    public @ResponseBody
    ResponseEntity<String> joinActivity(@PathVariable String id, @RequestBody EntryApplication application) {
        HttpStatus resCode = applyService.add(application);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取当前所有活动的所有申请")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/activities/{id}")
    public @ResponseBody
    ResponseEntity<List<EntryApplication>> getAllByActId(@PathVariable String id) {
        List<EntryApplication> entryApplications = applyService.getAllByActivity(id);
        HttpStatus resCode = entryApplications.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(entryApplications, resCode);
    }

    @ApiOperation(value = "操作申请结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "res", value = "申请结果0：accept|1:reject", required = true, dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 406, message = "NOT_ACCEPTABLE,人数已满")
    })
    @PostMapping(value = "/{id}/{res}")
    public @ResponseBody
    ResponseEntity<String> isPermit(@PathVariable String id, @PathVariable int res) {
        HttpStatus resCode = applyService.isPermit(id, res);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取指定id的用户的所有申请")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/user/{id}")
    public @ResponseBody
    ResponseEntity<List<EntryApplication>> getAllByUserId(@PathVariable String id) {
        List<EntryApplication> entryApplications = applyService.getAllByUserId(id);
        HttpStatus resCode = entryApplications.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(entryApplications, resCode);
    }

}

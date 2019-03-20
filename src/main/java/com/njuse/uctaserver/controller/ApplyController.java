package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.dto.ApplicationDTO;
import com.njuse.uctaserver.model.entity.Application;
import com.njuse.uctaserver.service.ActMemberService;
import com.njuse.uctaserver.service.ApplyService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "Application Controller")
@Controller
@RequestMapping("/activities")
public class ApplyController {

    private final ApplyService applyService;

    @Autowired
    public ApplyController(ApplyService applyService) {
        this.applyService = applyService;
    }

    @ApiOperation(value = "申请参加出游活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "applicationDTO", value = "申请参加详情DTO类", required = true, dataType = "ApplicationDTO", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 302, message = "Found"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @PostMapping(value = "/{id}/applications")
    public @ResponseBody
    ResponseEntity<String> joinActivity(@PathVariable String id, @RequestBody ApplicationDTO applicationDTO) {
        HttpStatus resCode;
        if (applicationDTO.getPwd() != null && applicationDTO.getPwd().equals("")) {
            resCode = applyService.pwdApply(applicationDTO.getUserId(), applicationDTO.getActId(), applicationDTO.getPwd());
        } else {
            Application application = new Application();
            BeanUtils.copyProperties(applicationDTO, application, "status");
            resCode = applyService.add(application);
        }
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "操作申请结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "res", value = "申请结果1：accept|-1:reject", required = true, dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 406, message = "NOT_ACCEPTABLE,人数已满")
    })
    @PostMapping(value = "/applications/{id}/{res}")
    public @ResponseBody
    ResponseEntity<String> isPermit(@PathVariable String id, @PathVariable int res) {
        HttpStatus resCode = applyService.isPermit(id, res);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "删除申请")
    @ApiImplicitParam(name = "id", value = "申请id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @DeleteMapping(value = "/applications/{id}")
    public @ResponseBody
    ResponseEntity<String> delete(@PathVariable String id) {
        HttpStatus resCode = applyService.delete(id);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取指定id的用户组织的活动的所有申请")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/applications/user/{id}")
    public @ResponseBody
    ResponseEntity<List<ApplicationDTO>> getAllByUserId(@PathVariable String id) {
        List<ApplicationDTO> entryApplications = applyService.getAllByUserId(id);
        HttpStatus resCode = entryApplications.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(entryApplications, resCode);
    }

}

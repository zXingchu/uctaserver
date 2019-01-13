package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.dto.InvitationDTO;
import com.njuse.uctaserver.model.entity.Invitation;
import com.njuse.uctaserver.service.InvitationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Invitation Controller")
@Controller
@RequestMapping("/activities/")
public class InvitationController {


    private final InvitationService invitationService;

    @Autowired
    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @ApiOperation(value = "申请参加出游活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "applicationDTO", value = "申请参加详情DTO类", required = true, dataType = "EntryApplicationDTO", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @PostMapping(value = "/{id}/invitations")
    public @ResponseBody
    ResponseEntity<String> joinActivity(@PathVariable String id, @RequestBody InvitationDTO invitationDTO) {

        HttpStatus resCode = invitationService.invite(invitationDTO.getActId(), invitationDTO.getUserId(), invitationDTO.getInviterId());
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取当前所有活动的所有申请")
    @ApiImplicitParam(name = "id", value = "出游活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/{id}/invitations")
    public @ResponseBody
    ResponseEntity<List<Invitation>> getAllByActId(@PathVariable String id) {
        List<Invitation> invitations = invitationService.getAllByActId(id);
        HttpStatus resCode = invitations.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(invitations, resCode);
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
    @PostMapping(value = "/invitations/{id}/{res}")
    public @ResponseBody
    ResponseEntity<String> isPermit(@PathVariable String id, @PathVariable int res) {
        HttpStatus resCode = invitationService.respond(id, res);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取指定id的用户的所有申请")
    @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/invitations/user/{id}")
    public @ResponseBody
    ResponseEntity<List<Invitation>> getAllByUserId(@PathVariable String id) {
        List<Invitation> invitations = invitationService.getAllByUserId(id);
        HttpStatus resCode = invitations.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(invitations, resCode);
    }


}

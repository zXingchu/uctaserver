package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.OrgComment;
import com.njuse.uctaserver.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Organization Comment Controller")
@Controller
@RequestMapping("/comment/org/")
public class OrgCommentController {

    @Autowired
    CommentService commentService;

    @ApiOperation(value = "获取指定id活动的所有评论")
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<List<OrgComment>> getAllByUserId(@PathVariable String id) {
        List<OrgComment> orgComments = commentService.getAllByActId(id);
        HttpStatus resCode = orgComments.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(orgComments, resCode);
    }

    @ApiOperation(value = "创建指定id活动的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "memberComment", value = "组织评论详情实体类", required = true, dataType = "OrgComment", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<String> create(@PathVariable String id, @RequestBody OrgComment orgComment) {
        HttpStatus resCode = commentService.addCommentOnOrg(orgComment);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }


    @ApiOperation(value = "获取指定id活动的评分")
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/{id}/score")
    public @ResponseBody
    ResponseEntity<Integer> getScore(@PathVariable String id) {
        int memberComment = commentService.getScoreByActId(id);
        return new ResponseEntity<>(memberComment, HttpStatus.OK);
    }

}

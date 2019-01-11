package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.dto.PlaceCommentDTO;
import com.njuse.uctaserver.model.entity.PlaceComment;
import com.njuse.uctaserver.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Place Comment Controller")
@Controller
@RequestMapping("/comment/place/")
public class PlaceCommentController {

    private final CommentService commentService;

    @Autowired
    public PlaceCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "获取指定id地点的所有评论")
    @ApiImplicitParam(name = "place", value = "地点名", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/{place}")
    public @ResponseBody
    ResponseEntity<List<PlaceComment>> getAllByUserId(@PathVariable String place) {
        List<PlaceComment> placeComments = commentService.getAllByPlace(place);
        HttpStatus resCode = placeComments.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(placeComments, resCode);
    }

    @ApiOperation(value = "创建指定id地点的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "place", value = "地点名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "placeCommentDTO", value = "地点评论详情DTO类", required = true, dataType = "PlaceCommentDTO", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{place}")
    public @ResponseBody
    ResponseEntity<String> create(@PathVariable String id, @RequestBody PlaceCommentDTO placeCommentDTO) {
        PlaceComment placeComment = new PlaceComment();
        BeanUtils.copyProperties(placeCommentDTO, placeComment);
        HttpStatus resCode = commentService.addCommentOnPlace(placeComment);
        return new ResponseEntity<>(resCode.getReasonPhrase(), resCode);
    }

    @ApiOperation(value = "获取指定id地点的评分")
    @ApiImplicitParam(name = "place", value = "地点名", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/{id}/score")
    public @ResponseBody
    ResponseEntity<Double> getScore(@PathVariable String place) {
        Double placeComment = commentService.getScoreByPlace(place);
        return new ResponseEntity<>(placeComment, HttpStatus.OK);
    }

}

package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.model.entity.PlaceComment;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "Place Comment Controller")
@Controller
@RequestMapping("/comment/place/")
public class PlaceCommentController {


    @ApiOperation(value = "获取指定id地点的所有评论")
    @ApiImplicitParam(name = "id", value = "地点id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @GetMapping(value = "/activity/{id}")
    public @ResponseBody
    ResponseEntity<List<PlaceComment>> getAllByUserId(@PathVariable String id) {
        List<PlaceComment> placeComment = new ArrayList<PlaceComment>();
        return new ResponseEntity<List<PlaceComment>>(placeComment, HttpStatus.OK);
    }

    @ApiOperation(value = "创建指定id地点的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "地点id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "placeComment", value = "地点评论详情实体类", required = true, dataType = "PlaceComment", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> create(@PathVariable String id, @RequestBody PlaceComment placeComment) {
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }

    @ApiOperation(value = "获取指定id地点的评分")
    @ApiImplicitParam(name = "id", value = "地点id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 420, message = "Method Failure"),
            @ApiResponse(code = 204, message = "No Content")
    })
    @GetMapping(value = "/activity/{id}/score")
    public @ResponseBody
    ResponseEntity<Integer> getScore(@PathVariable String id) {
        Integer placeComment = new Integer(10);
        return new ResponseEntity<Integer>(placeComment, HttpStatus.OK);
    }

}

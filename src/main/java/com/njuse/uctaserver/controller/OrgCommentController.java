package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.dto.OrgCommentDTO;
import com.njuse.uctaserver.model.entity.OrgComment;
import com.njuse.uctaserver.service.CommentService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Api(tags = "Organization Comment Controller")
@Controller
@RequestMapping("/comment/org/")
public class OrgCommentController {

    private final CommentService commentService;

    @Autowired
    public OrgCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "获取指定id活动的所有评论")
    @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not FOUND")
    })
    @GetMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<List<OrgCommentDTO>> getAllByUserId(@PathVariable String id) {
        List<OrgCommentDTO> orgComments = commentService.getAllByActId(id);
        HttpStatus resCode = orgComments.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        return new ResponseEntity<>(orgComments, resCode);
    }

    @ApiOperation(value = "创建指定id活动的评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "orgCommentDTO", value = "组织评论详情DTO类", required = true, dataType = "OrgCommentDTO", paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/{id}")
    public @ResponseBody
    ResponseEntity<String> create(@PathVariable String id, @RequestBody OrgCommentDTO orgCommentDTO) {
        OrgComment orgComment = new OrgComment();
        BeanUtils.copyProperties(orgCommentDTO, orgComment);
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
    ResponseEntity<Double> getScore(@PathVariable String id) {
        Double memberComment = commentService.getScoreByActId(id);
        return new ResponseEntity<>(memberComment, HttpStatus.OK);
    }

    @PostMapping(value="/uploadImg")
    public @ResponseBody
    ResponseEntity<String> uploadImg(@RequestPart("image") MultipartFile image) throws IOException {
//        String filename=image.getOriginalFilename();
        String filename=new Date().getTime()+".jpg";
        File dir= new File("/home/img");
        MultipartFile file=image;
        if (!(file.getOriginalFilename().equals(""))) {

            file.transferTo(new File(dir + "/" + filename));
        }
        System.out.print(filename);

        return new ResponseEntity<>(filename, HttpStatus.OK);
    }

}

package com.njuse.uctaserver.controller;

import com.njuse.uctaserver.dto.ActivityDTO;
import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.service.ActivityService;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
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

    private class AcitivtySpec implements Specification<Activity> {
        private String name;
        private String ownerId;
        private String userId;
        private String startTime;
        private String number;
        private String status;
        private String place;

        public AcitivtySpec(String name, String ownerId, String userId, String startTime, String number, String status, String place) {
            this.name = name;
            this.ownerId = ownerId;
            this.userId = userId;
            this.startTime = startTime;
            this.number = number;
            this.status = status;
            this.place = place;
        }

        @Override
        public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
            Predicate p = criteriaBuilder.notEqual(root.get("id"), "");
            if (name != null && !name.equals("")) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (ownerId != null && !ownerId.equals("")) {
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("ownerId"), "%" + ownerId + "%"));
            }
            return null;
        }
    }


    @ApiOperation(value = "获取当前所有活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "过滤条件 活动名", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "ownerId", value = "过滤条件 组织者id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "过滤条件 参加者id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startTime", value = "过滤条件 开始时间 startTime yyyy-MM-dd HH:mm", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "number", value = "过滤条件 参与最多人数 number", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "过滤条件 状态 status", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "place", value = "过滤条件 活动地点 place", required = false, dataType = "String", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 304, message = "Not Modified")
    })
    @GetMapping(value = "")
    public @ResponseBody
    ResponseEntity<List<Activity>> getAll(HttpSession httpSession,
                                          @RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "ownerId", required = false) String ownerId,
                                          @RequestParam(value = "userId", required = false) String userId,
                                          @RequestParam(value = "startTime", required = false) String startTime,
                                          @RequestParam(value = "number", required = false) String number,
                                          @RequestParam(value = "status", required = false) String status,
                                          @RequestParam(value = "place", required = false) String place) {
        String openid = String.valueOf(httpSession.getAttribute("openid"));
        List<Activity> activities = activityService.getAll();
        if (name != null && !name.equals(""))
            activities.retainAll(activityService.getAllByName(name));
        if (ownerId != null && !ownerId.equals(""))
            activities.retainAll(activityService.getAllByOwnerId(ownerId));
        if (userId != null && !userId.equals(""))
            activities.retainAll(activityService.getAllByUserId(userId));
        if (userId == null && ownerId == null && openid != null) {
            activities.removeAll(activityService.getAllByUserId(openid));
            activities.removeAll(activityService.getAllByOwnerId(openid));
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }

    @ApiOperation(value = "创建出游活动或更新指定id出游活动")
    @ApiImplicitParam(name = "activityDTO", value = "出游活动详情实体类 时间yyyy-MM-dd HH:mm", required = true, dataType = "ActivityDTO", paramType = "body")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 304, message = "Not Modified"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/")
    public @ResponseBody
    ResponseEntity<String> createOrUpdate(HttpSession httpSession, @RequestBody ActivityDTO activityDTO) {
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityDTO, activity, "status", "auditStatus");
        HttpStatus resCode = activityService.addOrUpdate(activity);
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
    @PostMapping(value = "audit/{id}/{res}")
    public @ResponseBody
    ResponseEntity<String> audit(@PathVariable String id, @PathVariable int res) {
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

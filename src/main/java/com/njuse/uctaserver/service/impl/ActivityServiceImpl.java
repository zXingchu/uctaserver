package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.ActivityMember;
import com.njuse.uctaserver.model.repo.ActMemberRepo;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.UserRepo;
import com.njuse.uctaserver.service.ActivityService;
import com.njuse.uctaserver.until.AuditStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepo activityRepo;

    private final UserRepo userRepo;

    private final ActMemberRepo actMemberRepo;

    @Autowired
    public ActivityServiceImpl(ActivityRepo activityRepo, UserRepo userRepo, ActMemberRepo actMemberRepo) {
        this.activityRepo = activityRepo;
        this.userRepo = userRepo;
        this.actMemberRepo = actMemberRepo;
    }

    @Override
    public HttpStatus addOrUpdate(Activity activity) {
        if(activity.getId() == null) {
            activityRepo.save(activity);
            ActivityMember activityMember = new ActivityMember();
            activityMember.setActId(activity.getId());
            activityMember.setUserId(activity.getOwnerId());
            actMemberRepo.save(activityMember);
            return HttpStatus.CREATED;
        }else if(!activityRepo.existsById(activity.getId()))
            return HttpStatus.NOT_FOUND;
        else
            activityRepo.save(activity);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus delete(String id) {
        if (!activityRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        activityRepo.deleteById(id);
        if (activityRepo.existsById(id)) {
            return HttpStatus.NOT_MODIFIED;
        }
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus audit(String id, int res) {
        if (!activityRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        Activity activity = activityRepo.getOne(id);
        activity.setAuditStatus(AuditStatus.getName(res));
        activityRepo.save(activity);
        return HttpStatus.OK;
    }

    @Override
    public Activity get(String id) {
        if (activityRepo.existsById(id)) {
            return activityRepo.getOne(id);
        }
        return null;
    }

    @Override
    public List<Activity> getAll() {
        List<Activity> activities = activityRepo.findAllByAuditStatusOrderByStartTimeDesc(AuditStatus.ACCEPT.getName());
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

    @Override
    public List<Activity> getAllAuditing() {
        List<Activity> activities = activityRepo.findAllByAuditStatus(AuditStatus.AUDIT.getName());
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

    @Override
    public List<Activity> getAllByName(String name) {
        List<Activity> activities = activityRepo.findAllByNameContainingOrderByStartTimeDesc(name);
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

    @Override
    public List<Activity> getAllByUserId(String userId) {
        if (!userRepo.existsById(userId)) {
            return Collections.emptyList();
        }
        List<Activity> activities = actMemberRepo.getAllActByUserId(userId);
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

    @Override
    public List<Activity> getAllByOwnerId(String ownerId) {
        if (!userRepo.existsById(ownerId)) {
            return Collections.emptyList();
        }
        List<Activity> activities = activityRepo.findAllByOwnerIdOrderByStartTimeDesc(ownerId);
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

}

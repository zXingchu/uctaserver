package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.EntryApplication;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.service.ActivityService;
import com.njuse.uctaserver.until.ActivityStatus;
import com.njuse.uctaserver.until.AuditStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepo activityRepo;

    @Autowired
    public ActivityServiceImpl(ActivityRepo activityRepo) {
        this.activityRepo = activityRepo;
    }

    @Override
    public HttpStatus add(Activity activity) {
        activity.setCreateTime(new Date());
        activity.setAuditStatus(AuditStatus.AUDIT.getName());
        activity.setStatus(ActivityStatus.BEFORE_ACT.getName());
        activityRepo.save(activity);
        if (activity.getId() != null)
            return HttpStatus.CREATED;
        return HttpStatus.NOT_MODIFIED;
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
    public HttpStatus update(Activity activity) {
        if (activityRepo.existsById(activity.getId())) {
            activityRepo.save(activity);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
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
        List<Activity> activities = activityRepo.findAllByAuditStatus(AuditStatus.ACCEPT.getName());
        return activities.isEmpty() ? null : activities;
    }

    @Override
    public List<Activity> getAllAuditing() {
        List<Activity> activities = activityRepo.findAllByAuditStatus(AuditStatus.AUDIT.getName());
        return activities.isEmpty() ? null : activities;
    }

    @Override
    public List<Activity> getAll(String userId) {
        List<EntryApplication> entryApplications;
        List<Activity> activities = activityRepo.findAllByOwnerId(userId);
        return activities.isEmpty() ? null : activities;
    }

    @Override
    public HttpStatus audit(String id, int res) {
        if (!activityRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        Activity activity = activityRepo.getOne(id);
        activity.setAuditStatus(AuditStatus.getName(res));
        return HttpStatus.NOT_ACCEPTABLE;
    }
}

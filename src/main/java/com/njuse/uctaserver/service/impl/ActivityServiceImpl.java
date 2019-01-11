package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.EntryApplication;
import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.EntryApplicationRepo;
import com.njuse.uctaserver.model.repo.UserRepo;
import com.njuse.uctaserver.service.ActivityService;
import com.njuse.uctaserver.until.ActivityStatus;
import com.njuse.uctaserver.until.AuditStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepo activityRepo;

    private final UserRepo userRepo;

    private final EntryApplicationRepo entryApplicationRepo;


    @Autowired
    public ActivityServiceImpl(ActivityRepo activityRepo, UserRepo userRepo, EntryApplicationRepo entryApplicationRepo) {
        this.activityRepo = activityRepo;
        this.userRepo = userRepo;
        this.entryApplicationRepo = entryApplicationRepo;
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
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

    @Override
    public List<Activity> getAllAuditing() {
        List<Activity> activities = activityRepo.findAllByAuditStatus(AuditStatus.AUDIT.getName());
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

    @Override
    public List<Activity> getAllByUserId(String userId) {
        if (!userRepo.existsById(userId)) {
            return Collections.emptyList();
        }
        List<EntryApplication> entryApplications = entryApplicationRepo.findAllByUserId(userId);
        List<Activity> activities = activityRepo.findAllByOwnerId(userId);
        for (EntryApplication entryApplication : entryApplications) {
            if (activityRepo.existsById(entryApplication.getActId()))
                activities.add(activityRepo.getOne(entryApplication.getActId()));
        }
        return activities.isEmpty() ? Collections.emptyList() : activities;
    }

    @Override
    public List<User> getUsersPartInAct(String actId) {
        if (!activityRepo.existsById(actId)) {
            return Collections.emptyList();
        }
        List<EntryApplication> entryApplications = entryApplicationRepo.findAllByActId(actId);
        List<User> users = new ArrayList<>();
        users.add(userRepo.getOne(activityRepo.getOne(actId).getOwnerId()));
        for (EntryApplication entryApplication : entryApplications) {
            if (userRepo.existsById(entryApplication.getUserId()))
                users.add(userRepo.getOne(entryApplication.getUserId()));
        }
        return users.isEmpty() ? Collections.emptyList() : users;
    }

    @Override
    public List<Activity> getAll(String hh) {
        return null;
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

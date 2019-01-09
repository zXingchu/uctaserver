package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    ActivityRepo activityRepo;

    @Override
    public HttpStatus add(Activity activity) {
        activityRepo.save(activity);
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus delete(String id) {
        if(activityRepo.existsById(id)){
            activityRepo.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public HttpStatus update(Activity activity) {
        if(activityRepo.existsById(activity.getId())){
            activityRepo.save(activity);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }

    @Override
    public Activity get(String id) {
        if(activityRepo.existsById(id)){
            return activityRepo.getOne(id);
        }
        return null;
    }

    @Override
    public List<Activity> getAll() {
        return activityRepo.findAll();
    }

    @Override
    public List<Activity> getAll(String userId) {
        return activityRepo.findAllByOwnerId(userId);
    }
}

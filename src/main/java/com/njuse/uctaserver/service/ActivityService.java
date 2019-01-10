package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.Activity;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ActivityService {

    public HttpStatus add(Activity activity);

    public HttpStatus delete(String id);

    public HttpStatus update(Activity activity);

    public Activity get(String id);

    public List<Activity> getAll();

    public List<Activity> getAllAuditing();

    public List<Activity> getAll(String userId);

    HttpStatus audit(String id, int res);
}

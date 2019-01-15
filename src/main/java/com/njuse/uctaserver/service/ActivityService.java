package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.Activity;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ActivityService {

    HttpStatus addOrUpdate(Activity activity);

    HttpStatus delete(String id);

    HttpStatus audit(String id, int res);

    Activity get(String id);

    List<Activity> getAll();

    List<Activity> getAllAuditing();

    List<Activity> getAllByName(String name);

    List<Activity> getAllByUserId(String userId);

    List<Activity> getAllByOwnerId(String ownerId);

}

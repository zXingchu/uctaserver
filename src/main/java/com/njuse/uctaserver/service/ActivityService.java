package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.User;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ActivityService {

    HttpStatus add(Activity activity);

    HttpStatus delete(String id);

    HttpStatus update(Activity activity);

    Activity get(String id);

    List<Activity> getAll();

    List<Activity> getAllAuditing();

    List<Activity> getAllByUserId(String userId);

    HttpStatus audit(String id, int res);

    List<User> getUsersPartInAct(String actId);

    List<Activity> getAll(String hh);

}

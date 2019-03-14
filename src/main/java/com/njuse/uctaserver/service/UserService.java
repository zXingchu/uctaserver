package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.User;
import org.springframework.http.HttpStatus;

public interface UserService {

    User get(String id);

    HttpStatus addOrUpdate(User user);

    HttpStatus likeOrThread(String id, int maCode);

    HttpStatus addLabel(String id, String label);

}

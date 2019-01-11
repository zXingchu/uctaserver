package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.User;
import org.springframework.http.HttpStatus;

public interface UserService {

    User get(String id);

    HttpStatus add(User user);

    HttpStatus likeOrThread(String id, int maCode);

}

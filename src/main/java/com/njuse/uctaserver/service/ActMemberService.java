package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.User;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface ActMemberService {

    HttpStatus delete(String actId, String userId);

    List<User> getUsersPartInAct(String actId);

}

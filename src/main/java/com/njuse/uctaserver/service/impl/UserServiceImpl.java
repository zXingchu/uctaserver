package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.model.repo.UserRepo;
import com.njuse.uctaserver.service.UserService;
import com.njuse.uctaserver.until.MemberAttitude;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public User get(String id) {
        if(!userRepo.existsById(id)) {
            return null;
        }
        return userRepo.getOne(id);
    }

    @Override
    public HttpStatus addOrUpdate(User user) {
        if(userRepo.existsById(user.getId())) {
            userRepo.save(user);
            return HttpStatus.OK;
        }
        userRepo.save(user);
        return HttpStatus.CREATED;
    }

    @Override
    public HttpStatus likeOrThread(String id, int maCode) {
        if(!userRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        User user = userRepo.getOne(id);
        if(maCode == MemberAttitude.LIKE.getIndex())
            user.setLikeNum(user.getLikeNum() + 1);
        else
            user.setTreadNum(user.getTreadNum() + 1);
        return HttpStatus.OK;
    }
}

package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.ActivityMember;
import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.model.repo.ActMemberRepo;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.UserRepo;
import com.njuse.uctaserver.service.ActMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class ActMemberServiceImpl implements ActMemberService {

    private final ActivityRepo activityRepo;

    private final ActMemberRepo actMemberRepo;


    @Autowired
    public ActMemberServiceImpl(ActivityRepo activityRepo, UserRepo userRepo, ActMemberRepo actMemberRepo) {
        this.activityRepo = activityRepo;
        this.actMemberRepo = actMemberRepo;
    }

    @Override
    public HttpStatus delete(String actId, String userId) {
        ActivityMember activityMember = actMemberRepo.findFirstByActIdAndUserId(actId, userId);
        if (activityMember == null)
            return HttpStatus.NOT_FOUND;
        actMemberRepo.delete(activityMember);
        Activity activity = activityRepo.getOne(actId);
        activity.setPartNumber(activity.getPartNumber() - 1);
        activityRepo.save(activity);
        return HttpStatus.OK;
    }

    @Override
    public List<User> getUsersPartInAct(String actId) {
        if (!activityRepo.existsById(actId)) {
            return Collections.emptyList();
        }
//        List<User> users = actMemberRepo.getUsersPartInAct(actId);
//        return users.isEmpty() ? Collections.emptyList() : users;
        return Collections.emptyList();
    }

}

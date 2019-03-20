package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.dto.ApplicationDTO;
import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.ActivityMember;
import com.njuse.uctaserver.model.entity.Application;
import com.njuse.uctaserver.model.repo.ActMemberRepo;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.ApplicationRepo;
import com.njuse.uctaserver.service.ApplyService;
import com.njuse.uctaserver.until.ApplyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class ApplyServiceImpl implements ApplyService {

    private final ApplicationRepo applicationRepo;

    private final ActivityRepo activityRepo;

    private final ActMemberRepo actMemberRepo;

    @Autowired
    public ApplyServiceImpl(ApplicationRepo applicationRepo, ActivityRepo activityRepo, ActMemberRepo actMemberRepo) {
        this.applicationRepo = applicationRepo;
        this.activityRepo = activityRepo;
        this.actMemberRepo = actMemberRepo;
    }

    @Override
    public HttpStatus add(Application application) {
        if(applicationRepo.existsByActIdAndUserId(application.getActId(), application.getUserId()))
            return HttpStatus.FOUND;
        applicationRepo.save(application);
        String id = application.getId();
        if (id != null) {
            return HttpStatus.CREATED;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @Override
    public HttpStatus delete(String id) {
        if (!applicationRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        applicationRepo.deleteById(id);
        if (applicationRepo.existsById(id))
            return HttpStatus.NOT_MODIFIED;
        return HttpStatus.OK;
    }

    @Override
    public Application get(String id) {
        if (!applicationRepo.existsById(id))
            return null;
        return applicationRepo.getOne(id);
    }

    @Override
    public List<ApplicationDTO> getAllByUserId(String userId) {
        List<ApplicationDTO> entryApplications = applicationRepo.getAllByUserId(userId);
        return entryApplications.isEmpty() ? Collections.emptyList() : entryApplications;
    }


    @Override
    public HttpStatus isPermit(String id, int resCode) {
        if (!applicationRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        Application application = applicationRepo.getOne(id);
        Activity activity = activityRepo.getOne(application.getActId());
        if(!application.getStatus().equals(ApplyStatus.getName(0)))
            return HttpStatus.ACCEPTED;
        int partNumber = actMemberRepo.countAllByActId(application.getActId());
        Boolean statement = partNumber >= activity.getNumber() && resCode == ApplyStatus.ACCEPT.getIndex();
        if (statement)
            return HttpStatus.NOT_MODIFIED;
        ActivityMember activityMember = new ActivityMember();
        if(resCode == 1) {
            activityMember.setUserId(application.getUserId());
            activityMember.setActId(application.getActId());
            actMemberRepo.save(activityMember);
        }
        application.setStatus(ApplyStatus.getName(resCode));
        applicationRepo.save(application);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus pwdApply(String userId, String actId, String pwd) {
        if (!activityRepo.existsById(actId))
            return HttpStatus.NOT_FOUND;
        Activity activity = activityRepo.getOne(actId);
        if(!activity.getPwd().equals(pwd)){
            return HttpStatus.UNAUTHORIZED;
        }
        if(actMemberRepo.existsByActIdAndUserId(actId, userId)){
            return HttpStatus.NOT_MODIFIED;
        }
        int partNumber = actMemberRepo.countAllByActId(actId);
        Boolean statement = partNumber >= activity.getNumber();
        if (statement)
            return HttpStatus.NOT_MODIFIED;
        ActivityMember activityMember = new ActivityMember();
        activityMember.setActId(actId);
        activityMember.setUserId(userId);
        actMemberRepo.save(activityMember);
        return HttpStatus.OK;
    }
}

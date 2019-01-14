package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.dto.ApplicationDTO;
import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.Application;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.ApplicationRepo;
import com.njuse.uctaserver.service.ApplyService;
import com.njuse.uctaserver.until.ApplyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@Service
public class ApplyServiceImpl implements ApplyService {

    private final ApplicationRepo applicationRepo;

    private final ActivityRepo activityRepo;

    @Autowired
    public ApplyServiceImpl(ApplicationRepo applicationRepo, ActivityRepo activityRepo) {
        this.applicationRepo = applicationRepo;
        this.activityRepo = activityRepo;
    }

    @Override
    public HttpStatus add(Application application) {
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
    public List<ApplicationDTO> getAllByActivity(String actId) {
        List<ApplicationDTO> entryApplications = applicationRepo.getAllByActId(actId);
        return entryApplications.isEmpty() ? Collections.emptyList() : entryApplications;
    }

    @Override
    public HttpStatus isPermit(String id, int resCode) {
        if (!applicationRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        Application application = applicationRepo.getOne(id);
        Activity activity = activityRepo.getOne(application.getActId());
        int oldStatus = ApplyStatus.getIndex(application.getStatus());
        Boolean statement = Objects.requireNonNull(ApplyStatus.getName(resCode)).equals(application.getStatus());
        statement = statement || activity.getNumber() >= activity.getPartNumber() && resCode == ApplyStatus.ACCEPT.getIndex();
        if (statement)
            return HttpStatus.NOT_MODIFIED;
        if (oldStatus != ApplyStatus.APPLY.getIndex())
            activity.setPartNumber(activity.getPartNumber() + 1);
        application.setStatus(ApplyStatus.getName(resCode));
        activityRepo.save(activity);
        applicationRepo.save(application);
        return HttpStatus.OK;
    }
}

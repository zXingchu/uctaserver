package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.EntryApplication;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.EntryApplicationRepo;
import com.njuse.uctaserver.service.ApplyService;
import com.njuse.uctaserver.until.ApplyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
public class ApplyServiceImpl implements ApplyService {

    private final EntryApplicationRepo entryApplicationRepo;

    private final ActivityRepo activityRepo;

    @Autowired
    public ApplyServiceImpl(EntryApplicationRepo entryApplicationRepo, ActivityRepo activityRepo) {
        this.entryApplicationRepo = entryApplicationRepo;
        this.activityRepo = activityRepo;
    }

    @Override
    public HttpStatus add(EntryApplication entryApplication) {
        entryApplication.setStatus(ApplyStatus.APPLY.getName());
        entryApplicationRepo.save(entryApplication);
        String id = entryApplication.getId();
        if (id != null) {
            return HttpStatus.CREATED;
        }
        return HttpStatus.NOT_MODIFIED;
    }

    @Override
    public HttpStatus delete(String id) {
        if (!entryApplicationRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        entryApplicationRepo.deleteById(id);
        if (entryApplicationRepo.existsById(id))
            return HttpStatus.NOT_MODIFIED;
        return HttpStatus.OK;
    }

    @Override
    public EntryApplication get(String id) {
        if (!entryApplicationRepo.existsById(id))
            return null;
        return entryApplicationRepo.getOne(id);
    }

    @Override
    public List<EntryApplication> getAllByUserId(String userId) {
        List<EntryApplication> entryApplications = entryApplicationRepo.findAllByUserId(userId);
        return entryApplications.isEmpty() ? Collections.emptyList() : entryApplications;
    }

    @Override
    public List<EntryApplication> getAllByActivity(String actId) {
        List<EntryApplication> entryApplications = entryApplicationRepo.findAllByActId(actId);
        return entryApplications.isEmpty() ? Collections.emptyList() : entryApplications;
    }

    @Override
    public HttpStatus isPermit(String id, int res) {
        if (!entryApplicationRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        EntryApplication entryApplication = entryApplicationRepo.getOne(id);
        Activity activity = activityRepo.getOne(entryApplication.getActId());
        if (activity.getPartNumber() < activity.getNumber()) {
            activity.setPartNumber(activity.getPartNumber() + 1);
            activityRepo.save(activity);
            entryApplication.setStatus(ApplyStatus.getName(res));
            entryApplicationRepo.save(entryApplication);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_ACCEPTABLE;
    }
}

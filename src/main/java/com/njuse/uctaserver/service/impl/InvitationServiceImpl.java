package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.Invitation;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.InvitationRepo;
import com.njuse.uctaserver.model.repo.UserRepo;
import com.njuse.uctaserver.service.InvitationService;
import com.njuse.uctaserver.until.InvitationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class InvitationServiceImpl implements InvitationService{

    private final InvitationRepo invitationRepo;

    private final ActivityRepo activityRepo;

    private final UserRepo userRepo;

    @Autowired
    public InvitationServiceImpl(InvitationRepo invitationRepo, ActivityRepo activityRepo, UserRepo userRepo) {
        this.invitationRepo = invitationRepo;
        this.activityRepo = activityRepo;
        this.userRepo = userRepo;
    }

    @Override
    public HttpStatus invite(String actId, String userId, String inviterId) {
        if(!activityRepo.existsById(actId))
            return HttpStatus.NOT_FOUND;
        if(!userRepo.existsById(userId))
            return HttpStatus.NOT_FOUND;
        Activity activity = activityRepo.getOne(actId);
        if(!activity.getOwnerId().equals(inviterId))
            return HttpStatus.UNAUTHORIZED;
        Invitation invitation = new Invitation();
        invitation.setActId(actId);
        invitation.setUserId(userId);
        invitation.setInviterId(inviterId);
        invitationRepo.save(invitation);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus add(Invitation invitation) {
        Activity activity = activityRepo.getOne(invitation.getActId());
        if(!activity.getOwnerId().equals(invitation.getInviterId()))
            return HttpStatus.UNAUTHORIZED;
        invitationRepo.save(invitation);
        return HttpStatus.OK;
    }

    @Override
    public HttpStatus respond(String id, int resCode) {
        if (!invitationRepo.existsById(id))
            return HttpStatus.NOT_FOUND;
        Invitation invitation = invitationRepo.getOne(id);
        Activity activity = activityRepo.getOne(invitation.getActId());
        int oldStatus = InvitationStatus.getIndex(invitation.getStatus());
        Boolean statement = Objects.requireNonNull(InvitationStatus.getName(resCode)).equals(invitation.getStatus());
        statement = statement || activity.getNumber() >= activity.getPartNumber() && resCode == InvitationStatus.ACCEPT.getIndex();
        if (statement)
            return HttpStatus.NOT_MODIFIED;
        if (oldStatus != InvitationStatus.INVITE.getIndex())
            activity.setPartNumber(activity.getPartNumber() + 1);
        invitation.setStatus(InvitationStatus.getName(resCode));
        activityRepo.save(activity);
        invitationRepo.save(invitation);
        return HttpStatus.OK;
    }

    @Override
    public List<Invitation> getAllByActId(String id) {
        List<Invitation> invitations = invitationRepo.findAllByActId(id);
        return invitations.isEmpty() ? Collections.emptyList() : invitations;
    }

    @Override
    public List<Invitation> getAllByUserId(String id) {
        List<Invitation> invitations = invitationRepo.findAllByUserId(id);
        return invitations.isEmpty() ? Collections.emptyList() : invitations;
    }


}

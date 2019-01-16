package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.entity.ActivityMember;
import com.njuse.uctaserver.model.entity.Invitation;
import com.njuse.uctaserver.model.repo.ActMemberRepo;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.model.repo.InvitationRepo;
import com.njuse.uctaserver.service.InvitationService;
import com.njuse.uctaserver.until.InvitationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService{

    private final InvitationRepo invitationRepo;

    private final ActivityRepo activityRepo;

    private final ActMemberRepo actMemberRepo;



    @Autowired
    public InvitationServiceImpl(InvitationRepo invitationRepo, ActivityRepo activityRepo, ActMemberRepo actMemberRepo) {
        this.invitationRepo = invitationRepo;
        this.activityRepo = activityRepo;
        this.actMemberRepo = actMemberRepo;
    }

    @Override
    public HttpStatus invite(String actId, String userId, String inviterId) {
        if(!activityRepo.existsById(actId))
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
        if(!invitation.getStatus().equals(InvitationStatus.getName(0)))
            return HttpStatus.ACCEPTED;
        int partNumber = actMemberRepo.countAllByActId(invitation.getActId());
        Boolean statement = partNumber >= activity.getNumber() && resCode == InvitationStatus.ACCEPT.getIndex();
        if (statement)
            return HttpStatus.NOT_MODIFIED;
        ActivityMember activityMember = new ActivityMember();
        if(resCode == 1) {
            activityMember.setUserId(invitation.getUserId());
            activityMember.setActId(invitation.getActId());
            actMemberRepo.save(activityMember);
        }
        invitation.setStatus(InvitationStatus.getName(resCode));
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

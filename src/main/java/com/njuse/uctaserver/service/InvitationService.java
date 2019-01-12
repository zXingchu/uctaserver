package com.njuse.uctaserver.service;

import com.njuse.uctaserver.model.entity.Invitation;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface InvitationService {

    HttpStatus invite(String actId, String userId, String inviterId);

    HttpStatus add(Invitation invitation);

    HttpStatus respond(String id, int resCode);

    List<Invitation> getAllByActId(String id);

    List<Invitation> getAllByUserId(String id);


}

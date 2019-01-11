package com.njuse.uctaserver.service;

import org.springframework.http.HttpStatus;

public interface InvitationService {

    HttpStatus invite(String actId, String userId, String inviterId);

    HttpStatus respond(String id, int resCode);

}

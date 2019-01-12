package com.njuse.uctaserver.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InvitationDTO {

    private String id;

    private String actId;

    private String userId;

    private Date time = new Date();

    private String inviterId;

}

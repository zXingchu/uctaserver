package com.njuse.uctaserver.dto;

import lombok.Data;
import java.util.Date;

@Data
public class InvitationDTO {

    private String id;

    private String actId;

    private String actName;

    private String userId;

    private String userName;

    private Date time = new Date();

    private String inviterId;

    private String inviterName;

    private String status;

}

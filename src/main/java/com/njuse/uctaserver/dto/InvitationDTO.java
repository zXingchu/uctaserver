package com.njuse.uctaserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class InvitationDTO {

    private String id;

    private String actId;

    private String actName;

    private String userId;

    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date time = new Date();

    private String inviterId;

    private String inviterName;

}

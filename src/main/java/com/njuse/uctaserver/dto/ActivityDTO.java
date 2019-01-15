package com.njuse.uctaserver.dto;


import com.njuse.uctaserver.until.ActivityStatus;
import com.njuse.uctaserver.until.AuditStatus;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ActivityDTO implements Serializable {

    private String id;

    private Date startTime;

    private Date endTime;

    private String name;

    private String description;

    private String place;

    private int number;

    private String ownerId;

    private String status = ActivityStatus.BEFORE_ACT.getName();

    private String auditStatus = AuditStatus.AUDIT.getName();

}

package com.njuse.uctaserver.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class ActivityDTO implements Serializable {

    private String id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date startTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date endTime;

    private String name;

    private String description;

    private String place;

    private int number;

    private String ownerId;

    private String status;

    private String auditStatus;

}

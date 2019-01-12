package com.njuse.uctaserver.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EntryApplicationDTO implements Serializable {

    private String id;

    private Date time;

    private String actId;

    private String userId;

    private String description;

}

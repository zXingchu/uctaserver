package com.njuse.uctaserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class EntryApplicationDTO implements Serializable {

    private String id;

    private String actId;

    private String userId;

    private String description;

}

package com.njuse.uctaserver.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDTO implements Serializable {

    private String id;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date time = new Date();

    private String actId;

    private String actName;

    private String userId;

    private String nickName;

    private String description;

    private String status;

}

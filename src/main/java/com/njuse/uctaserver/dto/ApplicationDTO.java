package com.njuse.uctaserver.dto;

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

    private Date time = new Date();

    private String actId;

    private String actName;

    private String userId;

    private String status;

    private String pwd = "";
}

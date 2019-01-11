package com.njuse.uctaserver.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrgCommentDTO implements Serializable {

    private String id;

    private String description;

    private double score;

    private String actId;

}

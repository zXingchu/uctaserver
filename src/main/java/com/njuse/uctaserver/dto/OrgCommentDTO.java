package com.njuse.uctaserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgCommentDTO implements Serializable {

    private String id;

    private String description;

    private double score;

    private String actId;

    private String avatarUrl;

    private String nickName;

    private String userId;
    private String img;

}

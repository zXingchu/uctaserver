package com.njuse.uctaserver.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PlaceCommentDTO implements Serializable {

    private String id;

    private String place;

    private String description;

    private double score;

}

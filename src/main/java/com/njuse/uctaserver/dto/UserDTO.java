package com.njuse.uctaserver.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String id;

    private String name;

    private int age;

    private int likeNum;

    private int treadNum;

    private String labels;
}

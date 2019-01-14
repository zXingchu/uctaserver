package com.njuse.uctaserver.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String id;

    private String name;

    private int gender;

    private String avatarUrl;

    private String city = "Nanjing";

    private String province = "Jiangsu";

    private int likeNum;

    private int treadNum;

    private String labels;
}

package com.njuse.uctaserver.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    private String id;

    private String name;

    private String weChatId;

    private int age;

    private int likeNum = 0;

    private int treadNum = 0;

    private String labels = "";
}

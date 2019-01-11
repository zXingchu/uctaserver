package com.njuse.uctaserver.until;

import lombok.Getter;

@Getter
public enum MemberAttitude {


    LIKE("点赞", 0), THREAD("点踩", 1);

    private String name;
    private int index;

    private MemberAttitude(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (MemberAttitude ma : MemberAttitude.values()) {
            if (ma.getIndex() == index) {
                return ma.name;
            }
        }
        return null;
    }

}

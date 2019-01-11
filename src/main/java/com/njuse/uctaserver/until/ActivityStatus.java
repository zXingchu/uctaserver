package com.njuse.uctaserver.until;

import lombok.Getter;

@Getter
public enum ActivityStatus {

    BEFORE_ACT("未开始", -1), IN_ACT("活动中", 0), AFTER_ACT("已结束", 1);

    private String name;
    private int index;

    private ActivityStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ActivityStatus as : ActivityStatus.values()) {
            if (as.getIndex() == index) {
                return as.name;
            }
        }
        return null;
    }

}

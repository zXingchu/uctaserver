package com.njuse.uctaserver.until;

import lombok.Getter;

@Getter
public enum ApplyStatus {

    APPLY("申请中", 2), ACCEPT("已通过", 0), REJECT("已拒绝", 1);

    private String name;
    private int index;

    private ApplyStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ApplyStatus as : ApplyStatus.values()) {
            if (as.getIndex() == index) {
                return as.name;
            }
        }
        return null;
    }
}

package com.njuse.uctaserver.until;

import lombok.Getter;

@Getter
public enum InvitationStatus {

    INVITE("邀请中", 0), ACCEPT("已通过", 1), REJECT("已拒绝", -1);

    private String name;

    private int index;

    private InvitationStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (InvitationStatus iStatus : InvitationStatus.values()) {
            if (iStatus.getIndex() == index) {
                return iStatus.name;
            }
        }
        return null;
    }

    public static int getIndex(String name) {
        for (InvitationStatus iStatus : InvitationStatus.values()) {
            if (iStatus.getName() == name) {
                return iStatus.index;
            }
        }
        return 250;
    }
}

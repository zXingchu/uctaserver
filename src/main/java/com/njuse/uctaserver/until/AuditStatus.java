package com.njuse.uctaserver.until;


import lombok.Getter;

@Getter
public enum AuditStatus {

    AUDIT("审核中", 0), ACCEPT("已通过", 1), REJECT("已拒绝", -1);

    private String name;
    private int index;

    private AuditStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (AuditStatus c : AuditStatus.values()) {
            if (c.getIndex() == index) {
                    return c.name;
            }
        }
        return null;
    }

}

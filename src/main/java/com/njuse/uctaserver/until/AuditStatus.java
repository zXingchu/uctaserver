package com.njuse.uctaserver.until;

public enum AuditStatus {

    AUDIT("审核", 2), ACCEPT("通过", 0), REJECT("拒绝", 1);

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}

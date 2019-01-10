package com.njuse.uctaserver.until;

public enum ApplyStatus {

    APPLY("申请中", 2), ACCEPT("已通过", 0), REJECT("已拒绝", 1);

    private String name;
    private int index;

    private ApplyStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ApplyStatus c : ApplyStatus.values()) {
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

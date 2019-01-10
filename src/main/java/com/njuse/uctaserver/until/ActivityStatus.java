package com.njuse.uctaserver.until;

public enum ActivityStatus {

    BEFORE_ACT("未开始", -1), IN_ACT("活动中", 0), AFTER_ACT("已结束", 1);

    private String name;
    private int index;

    private ActivityStatus(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ActivityStatus c : ActivityStatus.values()) {
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

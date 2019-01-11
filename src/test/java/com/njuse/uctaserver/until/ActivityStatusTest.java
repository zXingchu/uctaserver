package com.njuse.uctaserver.until;

import org.junit.Test;

import static org.junit.Assert.*;

public class ActivityStatusTest {

    @Test
    public void getName() {
        assertEquals("未开始", ActivityStatus.BEFORE_ACT.getName());
        assertEquals("活动中", ActivityStatus.IN_ACT.getName());
        assertEquals("已结束", ActivityStatus.AFTER_ACT.getName());
    }

    @Test
    public void getName1() {
        assertEquals("未开始", ActivityStatus.BEFORE_ACT.getName());
        assertEquals("活动中", ActivityStatus.IN_ACT.getName());
        assertEquals("已结束", ActivityStatus.AFTER_ACT.getName());
    }

    @Test
    public void getIndex() {
        assertEquals(-1, ActivityStatus.BEFORE_ACT.getIndex());
        assertEquals(0, ActivityStatus.IN_ACT.getIndex());
        assertEquals(1, ActivityStatus.AFTER_ACT.getIndex());
    }
}
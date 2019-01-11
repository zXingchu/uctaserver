package com.njuse.uctaserver.until;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplyStatusTest {

    @Test
    public void getName() {
        assertEquals("申请中", ApplyStatus.APPLY.getName());
        assertEquals("已通过", ApplyStatus.ACCEPT.getName());
        assertEquals("已拒绝", ApplyStatus.REJECT.getName());
    }

    @Test
    public void getIndex() {
        assertEquals(0, ApplyStatus.getIndex("申请中"));
        assertEquals(1, ApplyStatus.getIndex("已通过"));
        assertEquals(-1, ApplyStatus.getIndex("已拒绝"));
    }

    @Test
    public void getName1() {
        assertEquals("申请中", ApplyStatus.getName(0));
        assertEquals("已通过", ApplyStatus.getName(1));
        assertEquals("已拒绝", ApplyStatus.getName(-1));
    }

    @Test
    public void getIndex1() {
        assertEquals(0, ApplyStatus.APPLY.getIndex());
        assertEquals(1, ApplyStatus.ACCEPT.getIndex());
        assertEquals(-1, ApplyStatus.REJECT.getIndex());

    }
}
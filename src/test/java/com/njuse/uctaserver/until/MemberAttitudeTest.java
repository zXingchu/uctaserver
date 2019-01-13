package com.njuse.uctaserver.until;

import org.junit.Test;

import static org.junit.Assert.*;

public class MemberAttitudeTest {

    @Test
    public void getName() {
        assertEquals("点赞", MemberAttitude.LIKE.getName());
        assertEquals("点踩", MemberAttitude.THREAD.getName());
    }

    @Test
    public void getName1() {
        assertEquals("点赞", MemberAttitude.getName(0));
        assertEquals("点踩", MemberAttitude.getName(1));
    }

    @Test
    public void getIndex() {
        assertEquals(1, MemberAttitude.THREAD.getIndex());
        assertEquals(0, MemberAttitude.LIKE.getIndex());
    }
}
package com.njuse.uctaserver.until;

import org.junit.Test;

import static org.junit.Assert.*;

public class InvitationStatusTest {

    @Test
    public void getName() {
        assertEquals("邀请中", InvitationStatus.INVITE.getName());
        assertEquals("已通过", InvitationStatus.ACCEPT.getName());
        assertEquals("已拒绝", InvitationStatus.REJECT.getName());
    }

    @Test
    public void getIndex() {
        assertEquals(0, InvitationStatus.getIndex("邀请中"));
        assertEquals(1, InvitationStatus.getIndex("已通过"));
        assertEquals(-1, InvitationStatus.getIndex("已拒绝"));
    }

    @Test
    public void getName1() {
        assertEquals("邀请中", InvitationStatus.getName(0));
        assertEquals("已通过", InvitationStatus.getName(1));
        assertEquals("已拒绝", InvitationStatus.getName(-1));
    }

    @Test
    public void getIndex1() {
        assertEquals(0, InvitationStatus.INVITE.getIndex());
        assertEquals(1, InvitationStatus.ACCEPT.getIndex());
        assertEquals(-1, InvitationStatus.REJECT.getIndex());
    }
}
package com.njuse.uctaserver.until;

import org.junit.Test;

import static org.junit.Assert.*;

public class AuditStatusTest {

    @Test
    public void getName() {
        assertEquals("已拒绝", AuditStatus.REJECT.getName());
        assertEquals("审核中", AuditStatus.AUDIT.getName());
        assertEquals("已通过", AuditStatus.ACCEPT.getName());
    }

    @Test
    public void getName1() {
        assertEquals("已拒绝", AuditStatus.getName(-1));
        assertEquals("审核中", AuditStatus.getName(0));
        assertEquals("已通过", AuditStatus.getName(1));
    }

    @Test
    public void getIndex() {
        assertEquals(-1, AuditStatus.REJECT.getIndex());
        assertEquals(0, AuditStatus.AUDIT.getIndex());
        assertEquals(1, AuditStatus.ACCEPT.getIndex());
    }
}
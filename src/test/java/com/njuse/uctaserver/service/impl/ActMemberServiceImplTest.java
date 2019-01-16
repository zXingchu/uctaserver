package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.ActivityMember;
import com.njuse.uctaserver.model.repo.ActMemberRepo;
import com.njuse.uctaserver.service.ActMemberService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lenovo on 2019-01-12.
 */
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActMemberServiceImplTest {
    @Autowired
    ActMemberService actMemberService;
    @Autowired
    ActMemberRepo actMemberRepo;


    private String testid;
    private ActivityMember activityMember = new ActivityMember();

    @Before
    public void setUp() throws Exception {
        activityMember.setUserId("22");
        activityMember.setActId("2");
        activityMember.setId("3");
        actMemberRepo.save(activityMember);
    }

    @Test
    public void test02_delete() throws Exception {
        assertEquals(HttpStatus.NOT_FOUND.value(), actMemberService.deleteByUser("ss", "qq").value());
        assertEquals(HttpStatus.OK.value(), actMemberService.deleteByUser("2", "22").value());
    }

    @Test
    public void test01_getUsersPartInAct() throws Exception {
        assertEquals(Collections.emptyList(), actMemberService.getUsersPartInAct("45"));
        assertTrue(actMemberService.getUsersPartInAct("2后后后后后扩军军扩").size() == 0);
    }
}
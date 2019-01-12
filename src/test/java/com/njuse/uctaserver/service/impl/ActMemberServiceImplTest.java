package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.ActivityMember;
import com.njuse.uctaserver.service.ActMemberService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lenovo on 2019-01-12.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActMemberServiceImplTest {
	@Autowired
	ActMemberService actMemberService;

	@Test
	public void delete() throws Exception {
		ActivityMember activityMember=new ActivityMember();
		activityMember.setActId("2");
		activityMember.setUserId("123");


	}

	@Test
	public void getUsersPartInAct() throws Exception {

	}
}
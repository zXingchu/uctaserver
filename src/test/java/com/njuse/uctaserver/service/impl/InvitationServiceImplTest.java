package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Invitation;
import com.njuse.uctaserver.model.repo.InvitationRepo;
import com.njuse.uctaserver.service.InvitationService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lenovo on 2019-01-12.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class InvitationServiceImplTest {
	@Autowired
	InvitationService invitationService;
	@Autowired
	InvitationRepo invitationRepo;




	@Test
	public void invite() throws Exception {
		assertEquals(HttpStatus.NOT_FOUND.value(),invitationService.invite("weq","ss","sss").value());
		assertEquals(HttpStatus.NOT_FOUND.value(),invitationService.invite("2","ss","sss").value());
		assertEquals(HttpStatus.UNAUTHORIZED.value(),invitationService.invite("2","1","sss").value());
		assertEquals(HttpStatus.OK.value(),invitationService.invite("2","1","23").value());


	}

	@Test
	public void add() throws Exception {
		Invitation invitation = invitationRepo.getOne("1");
		assertEquals(HttpStatus.OK.value(),invitationService.add(invitation).value());
		Invitation invitation1 = invitationRepo.getOne("2");

		assertEquals(HttpStatus.UNAUTHORIZED.value(),invitationService.add(invitation1).value());



	}

	@Test
	public void respond() throws Exception {
		assertEquals(HttpStatus.NOT_FOUND.value(),invitationService.respond("ds",232).value());
		assertEquals(HttpStatus.NOT_MODIFIED.value(),invitationService.respond("1",-1).value());
		Invitation invitation=invitationRepo.getOne("2");
		invitation.setStatus("邀请中");
		invitationRepo.save(invitation);
		assertEquals(HttpStatus.OK.value(),invitationService.respond("2",1).value());




	}

	@Test
	public void getAllByActId() throws Exception {
		assertTrue(invitationService.getAllByActId("2").size()>0);
		assertTrue(invitationService.getAllByActId("434").size()==0);

	}

	@Test
	public void getAllByUserId() throws Exception {



	}
}
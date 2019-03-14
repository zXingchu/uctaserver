package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.*;
import com.njuse.uctaserver.model.repo.*;
import com.njuse.uctaserver.service.*;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lenovo on 2019-01-16.
 */
@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

	@Autowired
	ActivityService activityService;

	@Autowired
	ApplyService applyService;

	@Autowired
	UserService userService;

	@Autowired
	ActMemberService actMemberService;

	@Autowired
	CommentService commentService;

	@Autowired
	InvitationService invitationService;

	@Autowired
	ActivityRepo activityRepo;

	@Autowired
	UserRepo userRepo;

	@Autowired
	OrgCommentRepo orgCommentRepo;
	@Autowired
	PlaceCommentRepo placeCommentRepo;
	@Autowired
	ApplicationRepo applicationRepo;
	@Autowired
	ActMemberRepo actMemberRepo;
	private static String actid;
	private static String applyid1;
	private static String applyid2;

	@Test
	public void test_01_addUser() {
		User actLeader=new User();
		actLeader.setTreadNum(12);
		actLeader.setId("a1");
		actLeader.setLikeNum(12);
		actLeader.setAvatarUrl("weaa");
		actLeader.setCity("南京");
		actLeader.setGender(1);
		actLeader.setLabels("sad");
		actLeader.setNickName("actLeader");
		actLeader.setProvince("江苏");
		assertEquals(HttpStatus.CREATED.value(),userService.addOrUpdate(actLeader).value());
		actLeader=userService.get("a1");
		actLeader.setAvatarUrl("sadasfas");
		assertEquals(HttpStatus.OK.value(),userService.addOrUpdate(actLeader).value());
		actLeader.setTreadNum(12);
		actLeader.setId("a2");
		actLeader.setLikeNum(12);
		actLeader.setAvatarUrl("weaa");
		actLeader.setCity("南京");
		actLeader.setGender(1);
		actLeader.setLabels("sad");
		actLeader.setNickName("applyyes");
		actLeader.setProvince("江苏");
		userService.addOrUpdate(actLeader);
		actLeader.setTreadNum(12);
		actLeader.setId("a3");
		actLeader.setLikeNum(12);
		actLeader.setAvatarUrl("weaa");
		actLeader.setCity("南京");
		actLeader.setGender(1);
		actLeader.setLabels("sad");
		actLeader.setNickName("applyno");
		actLeader.setProvince("江苏");
		userService.addOrUpdate(actLeader);


	}

	@Test
	public void test_02_addActivity() {
		Activity activity=new Activity();

		activity.setStatus("未开始");
		activity.setAuditStatus("ss");
		activity.setCreateTime(new Date());
		activity.setDescription("sda");
		activity.setStartTime(new Date());
		activity.setEndTime(new Date());
		activity.setName("测试1");
		activity.setNumber(23);
		activity.setOwnerId("a1");

		activity.setPlace("南京大学");
		assertEquals(HttpStatus.CREATED.value(),activityService.addOrUpdate(activity).value());
		actid=activity.getId();
		activity.setDescription("sadasd");
		assertEquals(HttpStatus.OK.value(),activityService.addOrUpdate(activity).value());
		activity.setId("122asss");
		assertEquals(HttpStatus.NOT_FOUND.value(),activityService.addOrUpdate(activity).value());
		assertEquals(HttpStatus.NOT_FOUND.value(),activityService.audit("0fdsa",0).value());
		assertEquals(HttpStatus.OK.value(),activityService.audit(actid,1).value());
		assertTrue(activityService.getAll().size()>0);
		assertTrue(activityService.getAllAuditing().size()>0);
		assertTrue(activityService.getAllByOwnerId("a1").size()>0);

	}

	@Test
	public void test_03_addApplyActivity() {
		Application application=new Application();
		application.setStatus("申请中");
		application.setActId(actid);
		application.setTime(new Date());
		application.setUserId("a2");
		assertEquals(HttpStatus.CREATED.value(),applyService.add(application).value());

		application.setTime(new Date());
		assertEquals(HttpStatus.FOUND.value(),applyService.add(application).value());
		applyid1=application.getId();

		Application application1=new Application();
		application1.setStatus("申请中");
		application1.setActId(actid);
		application1.setTime(new Date());
		application1.setUserId("a3");
		assertEquals(HttpStatus.CREATED.value(),applyService.add(application1).value());
		applyid2=application1.getId();


	}

	@Test
	public void test_04_respondApplyActivity() {

		assertEquals(HttpStatus.NOT_FOUND.value(),applyService.isPermit("asdasks",1).value());
		assertEquals(HttpStatus.OK.value(),applyService.isPermit(applyid1,1).value());
		assertEquals(HttpStatus.OK.value(),applyService.isPermit(applyid2,-1).value());
		assertTrue(applyService.getAllByUserId("a2").size()>=0);
		assertEquals(null,applyService.get("wq0efa"));
		assertEquals(applyService.get(applyid1),applyService.get(applyid1));
		assertTrue(actMemberService.getUsersPartInAct(actid).size()>0);
		assertTrue(activityService.getAllByUserId("a2").size()>0);
		assertTrue(applicationRepo.getAllByUserId("plpasd").size()>=0);
		assertTrue(actMemberRepo.getUsersPartInAct("sdav").size()>=0);
		assertTrue(actMemberRepo.getAllActByUserId("sad").size()>=0);
		assertTrue(orgCommentRepo.findAllByActId("sad").size()>=0);
	}

	@Test
	public void test_05_comment(){
		OrgComment orgComment=new OrgComment();
		orgComment.setActId(actid);
		orgComment.setScore(9);
		orgComment.setDescription("sadadaa");
		assertEquals(HttpStatus.CREATED.value(),commentService.addCommentOnOrg(orgComment).value());
		String orgid=orgComment.getId();
		PlaceComment placeComment=new PlaceComment();
		placeComment.setDescription("sadaf");
		placeComment.setScore(8);
		placeComment.setPlace("南京大学");
		assertEquals(HttpStatus.CREATED.value(),commentService.addCommentOnPlace(placeComment).value());
		String plaid=placeComment.getId();
		assertTrue(commentService.getAllByActId(actid).size()>=0);
		assertTrue(commentService.getScoreByPlace("南京大学")>0);
		assertTrue(commentService.getScoreByActId("3")<0);
		assertTrue(commentService.getScoreByActId(actid)>0);
		orgCommentRepo.deleteById(orgid);
		placeCommentRepo.deleteById(plaid);

	}
	@Test
	public void test_06_likeorthread(){
		assertEquals(HttpStatus.NOT_FOUND.value(),userService.likeOrThread("w0eu9h",0).value());
		assertEquals(HttpStatus.OK.value(),userService.likeOrThread("a1",1).value());
		assertEquals(HttpStatus.OK.value(),userService.likeOrThread("a1",0).value());
	}
	@Test
	public void test_099_delUser(){
		assertEquals(HttpStatus.NOT_FOUND.value(),actMemberService.deleteByUser("sdfasda","fsada").value());
		assertEquals(HttpStatus.OK.value(),actMemberService.deleteByUser(actid,"a2").value());

		assertEquals(HttpStatus.NOT_FOUND.value(),activityService.delete("poo").value());
		assertEquals(HttpStatus.OK.value(),activityService.delete(actid).value());
		assertEquals(HttpStatus.NOT_FOUND.value(),applyService.delete("dgs").value());
		System.out.println("llllllllllllllllllllllllllllllll"+applyid1);
		System.out.println("llllllllllllllllllllllllllllllll"+applyid2);
		assertEquals(HttpStatus.OK.value(),applyService.delete(applyid1).value());
		assertEquals(HttpStatus.OK.value(),applyService.delete(applyid2).value());
		userRepo.deleteById("a1");
		userRepo.deleteById("a2");
		userRepo.deleteById("a3");

	}


}

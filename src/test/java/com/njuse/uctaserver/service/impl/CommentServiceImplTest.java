package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.dto.OrgCommentDTO;
import com.njuse.uctaserver.model.entity.OrgComment;
import com.njuse.uctaserver.model.entity.PlaceComment;
import com.njuse.uctaserver.service.CommentService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lenovo on 2019-01-12.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentServiceImplTest {
	@Autowired
	CommentService commentService;

	private static String orgid;
	private static String plaid;
	private OrgComment orgComment=new OrgComment();
	private PlaceComment placeComment=new PlaceComment();


	@Before
	public void setUp() throws Exception{
		orgComment.setDescription("sss");
		orgComment.setActId("2");
		orgComment.setScore(89);
		placeComment.setScore(87);
		placeComment.setDescription("saasda");
		placeComment.setPlace("南京大学");



	}

	@Test
	public void test06_getAllByActId() throws Exception {
		List<OrgCommentDTO> orgComments=commentService.getAllByActId("2");
		assertTrue(orgComments.size()>=0);

	}

	@Test
	public void test07_getAllByPlace() throws Exception {
		List<PlaceComment> placeComments=commentService.getAllByPlace("南京大学");
		assertTrue(placeComments.size()>0);

	}

	@Test
	public void test01_addCommentOnOrg() throws Exception {
		HttpStatus httpStatus=commentService.addCommentOnOrg(orgComment);
		assertEquals(HttpStatus.CREATED.value(),httpStatus.value());
	}

	@Test
	public void test02_addCommentOnPlace() throws Exception {
		HttpStatus httpStatus=commentService.addCommentOnPlace(placeComment);
		assertEquals(HttpStatus.CREATED.value(),httpStatus.value());

	}

	@Test
	public void test03_getScoreByActId() throws Exception {
		assertTrue(commentService.getScoreByActId("2")>0);
		assertTrue(commentService.getScoreByActId("32")<0);

	}

	@Test
	public void test04_getScoreByPlace() throws Exception {
		assertTrue(commentService.getScoreByPlace("南京大学")>0);
		assertTrue(commentService.getScoreByPlace("南sda")<0);
	}
}
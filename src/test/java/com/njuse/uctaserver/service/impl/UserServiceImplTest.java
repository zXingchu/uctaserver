package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.User;
import com.njuse.uctaserver.service.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by lenovo on 2019-01-13.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
	@Autowired
	UserService userService;

	private  String testid;

	@Test
	public void get() throws Exception {
		assertEquals(null,userService.get("34"));

	}

	@Test
	public void add() throws Exception {
		User user=userService.get("1");
		assertEquals(HttpStatus.OK.value(),userService.addOrUpdate(user).value());
		User a=new User();
		a.setId("2");
		a.setAge(22);
		a.setLabels("ssss");
		a.setLikeNum(21);
		a.setName("是大大撒");
		a.setTreadNum(12);
		assertEquals(HttpStatus.CREATED.value(),userService.addOrUpdate(a).value());

	}

	@Test
	public void likeOrThread() throws Exception {
		assertEquals(HttpStatus.NOT_FOUND.value(),userService.likeOrThread("434",12).value());
		assertEquals(HttpStatus.OK.value(),userService.likeOrThread("1",0).value());
		assertEquals(HttpStatus.OK.value(),userService.likeOrThread("1",1).value());


	}
}
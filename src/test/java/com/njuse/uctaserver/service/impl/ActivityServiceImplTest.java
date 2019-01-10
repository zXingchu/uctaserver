package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.model.repo.ActivityRepo;
import com.njuse.uctaserver.service.ActivityService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityServiceImplTest {

    @Autowired
    ActivityService activityService;

    private static String testId;

    private String testUserId = "470be3456a5a4935b09df1268689adf4";

    private Activity activity = new Activity();

    @Before
    public void setUp() throws Exception {
        activity.setName("test");
        activity.setStatus("测试");
        activity.setNumber(10);
        activity.setPartNumber(10);
        activity.setOwnerId("470be3456a5a4935b09df1268689adf4");
        activity.setAuditStatus("未审核");
        activity.setCreateTime(new Date());
        activity.setStartTime(new Date());
        activity.setEndTime(new Date());
        activity.setDescription("test");
        activity.setName("test");
    }

    @Test
    public void test_01_add() {
        assertEquals(activityService.add(activity).value(), HttpStatus.CREATED.value());
        testId = activity.getId();
    }

    @Test
    public void test_99_delete() {
        if(activityService.get(testId) != null)
            assertEquals(HttpStatus.OK.value(), activityService.delete(testId).value());
        else
            assertEquals(HttpStatus.NOT_FOUND.value(), activityService.delete(testId).value());
    }

    @Test
    public void test_03_update() {
        Activity activity2 = activityService.get(testId);
        String testStr = "测试通过";
        activity2.setStatus(testStr);
        activityService.update(activity2);
        Activity activity3 = activityService.get(testId);
        assertEquals(testStr, activity3.getStatus());
    }

    @Test
    public void test_02_get() {
        Activity activity2 = activityService.get(testId);
        assertEquals(activity.getName(), activity2.getName());
    }

    @Test
    public void test_04_getAll() {
        List<Activity> activities = activityService.getAll();
        assertTrue(activities.size() > 0);
    }

    @Test
    public void test_05_getAll1() {
        List<Activity> activities = activityService.getAll(testUserId);
        assertTrue(activities.size() > 0);
    }
}
package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.dto.ActivityDTO;
import com.njuse.uctaserver.model.entity.Activity;
import com.njuse.uctaserver.service.ActivityService;
import com.njuse.uctaserver.until.AuditStatus;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        assertEquals(activityService.addOrUpdate(activity).value(), HttpStatus.CREATED.value());
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
        Activity activityDTO =new Activity();
        BeanUtils.copyProperties(activity2, activityDTO);
        String testStr = "测试通过";
        activityDTO.setDescription(testStr);
        activityDTO.setAuditStatus(AuditStatus.ACCEPT.getName());
        activityService.addOrUpdate(activityDTO);
        Activity activity3 = activityService.get(testId);
        assertEquals(testStr, activity3.getDescription());
        List<Activity> activities = activityService.getAll();
        assertTrue(activities.size() > 0);
        Activity activity1 = new Activity();
        activity1.setId("张文卿");
		Activity activityDTO1=new Activity();
		BeanUtils.copyProperties(activity1,activityDTO1);

		HttpStatus httpStatus = activityService.addOrUpdate(activityDTO1);
        assertEquals(HttpStatus.NOT_FOUND.value(), httpStatus.value());



        Activity activityReject = activityService.get(testId);
		Activity activityDTOR =new Activity();
		BeanUtils.copyProperties(activityReject, activityDTOR);
        String testStr2 = "测试未通过";

        activityDTOR.setDescription(testStr2);
		activityDTOR.setAuditStatus(AuditStatus.REJECT.getName());
        activityService.addOrUpdate(activityDTOR);
        Activity activityR = activityService.get(testId);
        assertEquals(testStr2, activityR.getDescription());

        Activity activityAudit = activityService.get(testId);
		Activity activityDTOAudit =new Activity();
		BeanUtils.copyProperties(activityAudit, activityDTOAudit);
        String testStr1 = "测试中";
		activityDTOAudit.setDescription(testStr1);
		activityDTOAudit.setAuditStatus(AuditStatus.AUDIT.getName());
        activityService.addOrUpdate(activityDTOAudit);
        Activity activityA = activityService.get(testId);
        assertEquals(testStr1, activityA.getDescription());

    }

    @Test
    public void test_02_get() {
        Activity activity2 = activityService.get(testId);
        assertEquals(activity.getName(), activity2.getName());
        Activity activity3 = activityService.get("ss");
       assertEquals(null,activity3);

    }



    @Test
    public void test_05_getAll1() {
        List<Activity> activities = activityService.getAllByUserId(testUserId);
        assertTrue(activities.size()==0);
    }
    @Test
    public void test_06_getAllAuditing() {
        List<Activity> activities = activityService.getAllAuditing();
        assertTrue(activities.size() > 0);
    }

    @Test
    public void test_07_getAllByName() {
        List<Activity> activities = activityService.getAllByName("急急急");
        assertTrue(activities.size() > 0);
        assertTrue(activityService.getAllByName("wqw").size() == 0);


    }

    @Test
    public void test_08_getAllByUserId(){
        List<Activity> activities=activityService.getAllByUserId("22");
        assertTrue(activities.size() == 0);
        assertTrue(activityService.getAllByUserId("32").size() == 0);



    }
    @Test
    public void test_09_getAllByOwner(){
        List<Activity> activities=activityService.getAllByOwnerId("1832247");
        assertTrue(activities.size() > 0);
        assertTrue(activityService.getAllByOwnerId("dsfszz").size()==0);
    }

    @Test
    public void test_10_audit() {
        assertEquals(HttpStatus.NOT_FOUND.value(),activityService.audit("sa",2).value());
        assertEquals(HttpStatus.OK.value(),activityService.audit(testId,2).value());
    }

}
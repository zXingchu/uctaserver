package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.dto.ApplicationDTO;
import com.njuse.uctaserver.model.entity.Application;
import com.njuse.uctaserver.service.ApplyService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplyServiceImplTest {
    @Autowired
    ApplyService applyService;

    private static String testid;
    private Application application = new Application();

    @Before
    public void setUp() throws Exception {
        application.setActId("2");
        application.setUserId("213124");
        application.setStatus("申请中");
    }

    @Test
    public void test01_add() {
        assertEquals(applyService.add(application).value(), HttpStatus.CREATED.value());
        testid = application.getId();
    }

    @Test
    public void test099_delete() {
        assertEquals(HttpStatus.NOT_FOUND.value(), applyService.delete("555555").value());
        assertEquals(HttpStatus.OK.value(), applyService.delete(testid).value());
    }

//    @Test
//    public void test02_update() {
//        Application entryApplication1=applyService.get(testid);
//        entryApplication1.setTime(new Date());
//    }

    @Test
    public void test02_get() {
        Application application1 = applyService.get(testid);
        application.setId(testid);
        application.setTime(application1.getTime());
        assertEquals(application1, application);
        assertEquals(null, applyService.get("222222"));
    }

    @Test
    public void test03_getAllByUserId() {
        List<ApplicationDTO> applicationList = applyService.getAllByUserId("213124");
        assertTrue(applicationList.size() > 0);
        assertEquals(Collections.emptyList(), applyService.getAllByUserId("33"));
    }

    @Test
    public void test04_getAllByActivity() {
        List<ApplicationDTO> applicationList = applyService.getAllByActivity("1");
        assertTrue(applicationList.size() >= 0);
        assertEquals(Collections.emptyList(), applyService.getAllByActivity("33"));


    }

    @Test
    public void test05_isPermit() {
        assertEquals(HttpStatus.NOT_FOUND.value(), applyService.isPermit("hdff", 2).value());
        Application application1 = applyService.get(testid);
        application1.setStatus("已通过");
        assertEquals(HttpStatus.NOT_MODIFIED.value(), applyService.isPermit(testid, 0).value());
        assertEquals(HttpStatus.OK.value(), applyService.isPermit(testid, -1).value());
//        application1.setStatus("申请中");
    }
}
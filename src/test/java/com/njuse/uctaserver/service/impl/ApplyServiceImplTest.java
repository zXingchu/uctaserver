package com.njuse.uctaserver.service.impl;

import com.njuse.uctaserver.model.entity.EntryApplication;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplyServiceImplTest {
    @Autowired
    ApplyService applyService;

    private  static  String testid;
    private EntryApplication entryApplication=new EntryApplication();

    @Before
    public void setUp() throws Exception{
        entryApplication.setActId("2");
        entryApplication.setUserId("213124");
        entryApplication.setDescription("是多少啊");
        entryApplication.setStatus("申请中");
        entryApplication.setTime(new Date());

    }

    @Test
    public void test01_add() {
        assertEquals(applyService.add(entryApplication).value(), HttpStatus.CREATED.value());
        testid=entryApplication.getId();
    }

    @Test
    public void test099_delete() {
        assertEquals(HttpStatus.NOT_FOUND.value(),applyService.delete("555555").value());
        assertEquals(HttpStatus.OK.value(),applyService.delete(testid).value());
    }

//    @Test
//    public void test02_update() {
//        EntryApplication entryApplication1=applyService.get(testid);
//        entryApplication1.setTime(new Date());
//    }

    @Test
    public void test02_get() {
        EntryApplication entryApplication1=applyService.get(testid);
        entryApplication.setId(testid);
        entryApplication.setTime(entryApplication1.getTime());
        assertEquals(entryApplication1,entryApplication);
        assertEquals(null,applyService.get("222222"));
    }

    @Test
    public void test03_getAllByUserId() {
        List<EntryApplication> entryApplicationList=applyService.getAllByUserId("213124");
        assertTrue(entryApplicationList.size()>0);
        assertEquals(Collections.emptyList(),applyService.getAllByUserId("33"));
    }

    @Test
    public void test04_getAllByActivity() {
        List<EntryApplication> entryApplicationList=applyService.getAllByActivity("2");
        assertTrue(entryApplicationList.size()>0);
        assertEquals(Collections.emptyList(),applyService.getAllByActivity("33"));


    }


}
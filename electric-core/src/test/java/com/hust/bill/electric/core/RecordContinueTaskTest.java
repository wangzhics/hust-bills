package com.hust.bill.electric.core;


import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.task.record.RecordContinueTask;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RecordContinueTaskTest {

	@Autowired
	private IBuildingService buildingService;
	
	@Autowired 
	private IRoomService roomService;
	
	@Autowired 
	private IRecordService recordService;
	
	@Test
	public void test() {
		try {
			BigInteger taskId = BigInteger.valueOf(47);
			RecordContinueTask continueTask = new RecordContinueTask(taskId, buildingService, roomService, recordService);
			continueTask.create();
			Thread t = new Thread(continueTask);
			t.start();
			while(true) {
				System.out.println(continueTask.getProgress());
				Thread.sleep(5000);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

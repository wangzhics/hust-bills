package com.hust.bill.electric.core;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.task.consume.ConsumeCalculateTask;
import com.hust.bill.electric.core.task.record.RecordScanTask;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IConsumeService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class ConsumeCalculateTaskTest {

	@Autowired
	private IBuildingService buildingService;
	
	@Autowired 
	private IRoomService roomService;
	
	@Autowired 
	private IRecordService recordService;
	
	@Autowired 
	private IConsumeService consumeService;
	
	@Test
	public void test() {
		try {
			ConsumeCalculateTask calculateTask = new ConsumeCalculateTask(consumeService, buildingService, roomService, recordService);
			calculateTask.create();
			Thread t = new Thread(calculateTask);
			t.start();
			while(true) {
				System.out.println(calculateTask.getProgress());
				Thread.sleep(1000);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

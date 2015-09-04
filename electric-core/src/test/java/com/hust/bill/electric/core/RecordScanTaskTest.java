package com.hust.bill.electric.core;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.task.record.RecordScanTask;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RecordScanTaskTest {

	@Autowired
	private IBuildingService buildingService;
	
	@Autowired 
	private IRoomService roomService;
	
	@Autowired 
	private IRecordService recordService;
	
	@Test
	public void test() {
		try {
			RecordScanTask scanTask = new RecordScanTask(buildingService, roomService, recordService);
			scanTask.create();
			Thread t = new Thread(scanTask);
			t.start();
			while(true) {
				//System.out.println(scanTask.getProgress());
				Thread.sleep(10000);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

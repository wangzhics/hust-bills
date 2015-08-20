package com.hust.bill.electric.core;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.task.record.RecordScanStater;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RecordScanStaterTest {

	@Autowired
	private IBuildingService buildingService;
	
	@Autowired IRecordService recordService;
	
	@Test
	public void test() {
		try {
			RecordScanStater stater = new RecordScanStater(buildingService, recordService);
			stater.start();
			Thread.sleep(3600000);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

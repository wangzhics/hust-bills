package com.hust.bill.electric.core;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.task.room.RoomScanStater;
import com.hust.bill.electric.service.IBuildingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RoomScanStaterTest {

	@Autowired
	private IBuildingService buildingService;
	
	@Test
	public void test() {
		try {
			RoomScanStater stater = new RoomScanStater(buildingService);
			stater.start();
			Thread.sleep(100000);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

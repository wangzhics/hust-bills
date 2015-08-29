package com.hust.bill.electric.core;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.task.building.BuilidngScanTask;
import com.hust.bill.electric.core.task.room.RoomScanTask;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RoomScanTaskTest {

	@Autowired
	private IBuildingService buildingService;
	
	@Autowired IRoomService roomService;
	
	@Test
	public void test() {
		try {
			Building b = new Building("东区", "东一舍", 3);
			try {
				RoomScanTask scanAllTask = new RoomScanTask(b, roomService, true);
				Thread t = new Thread(scanAllTask);
				t.start();
				while(true) {
					System.out.println(scanAllTask.getProgress());
					Thread.sleep(100);
				}
			} catch (DataAccessException e) {
				e.printStackTrace();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			Thread.sleep(3600000);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

package com.hust.bill.electric.core;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.core.task.TaskManager;
import com.hust.bill.electric.core.task.room.InitialRoomStater;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRoomService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class InitialRoomStaterTest {


	@Autowired
	private IBuildingService buildingService;
	
	@Autowired 
	private IRoomService roomService;
	
	@Test
	public void test() {
		try {
			InitialRoomStater stater = new InitialRoomStater(buildingService, roomService);
			Thread t = new Thread(stater);
			t.start();
			while(true) {
				RoomTaskBean[] taskBeans = roomService.getAllTask();
				for(RoomTaskBean roomTask : taskBeans) {
					System.out.println(roomTask.getStatus().getDescription() + " - " + TaskManager.getInstance().getTaskProgress(roomTask.getName()));
				}
				Thread.sleep(1000);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

package com.hust.bill.electric.core;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.task.building.BuildingInitialTask;
import com.hust.bill.electric.core.task.building.BuildingScanTask;
import com.hust.bill.electric.service.IBuildingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class BuildingInitialTaskTest {
	
	@Autowired
	private IBuildingService buildingService;
	
	@Test
	public void test() {
		
		try {
			BuildingInitialTask scanAllTask = new BuildingInitialTask(buildingService);
			scanAllTask.create();
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
	}

}

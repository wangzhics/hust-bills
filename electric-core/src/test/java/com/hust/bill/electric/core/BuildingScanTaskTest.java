package com.hust.bill.electric.core;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.task.building.BuildingScanTask;
import com.hust.bill.electric.service.IBuildingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class BuildingScanTaskTest {
	
	@Autowired
	private IBuildingService buildingService;
	
	@Test
	public void test() {
		
		try {
			BuildingScanTask buildingScanTask = new BuildingScanTask(buildingService);
			buildingScanTask.create();
			Thread t = new Thread(buildingScanTask);
			t.start();
			while(true) {
				System.out.println(buildingScanTask.getProgress());
				Thread.sleep(100);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

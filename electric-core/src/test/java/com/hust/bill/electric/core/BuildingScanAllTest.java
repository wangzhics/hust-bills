package com.hust.bill.electric.core;


import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;
import com.hust.bill.electric.core.scan.building.BuildingScanResult;
import com.hust.bill.electric.core.scan.building.BuildingScanStater;
import com.hust.bill.electric.core.task.building.InitialBuildingTask;
import com.hust.bill.electric.core.task.building.ScanAllTask;
import com.hust.bill.electric.service.IBuildingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class BuildingScanAllTest {
	
	@Autowired
	private IBuildingService buildingService;
	
	@Test
	public void test() {
		
		try {
			ScanAllTask scanAllTask = new ScanAllTask(buildingService);
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

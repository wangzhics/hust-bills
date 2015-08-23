package com.hust.bill.electric.core;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hust.bill.electric.core.scan.building.BuildingScanResult;
import com.hust.bill.electric.core.scan.building.BuildingScanStater;
import com.hust.bill.electric.service.IBuildingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class BuildingScanStaterTest {
	
	private ExecutorService executorService = Executors.newSingleThreadExecutor();
	
	@Test
	public void test() {
		
		try {
			BuildingScanStater stater = new BuildingScanStater();
			Future<BuildingScanResult> future = executorService.submit(stater);
			BuildingScanResult result = future.get();
			System.out.println(result.getSuccessAreaList());
			System.out.println(result.getUnSuccessAreaList());
			System.out.println(result.getBuildingList());
			Thread.sleep(10000);
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}

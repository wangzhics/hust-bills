package com.hust.bill.electric.core.task.building;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.service.IBuildingService;

public class BuildingScanStater extends Thread {

	private IBuildingService buildingService;
	
	ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	private List<Building> buildingList = new ArrayList<Building>(100);
	
	List<Future<Building[]>> resultList = new ArrayList<Future<Building[]>>(10);  
	
	public BuildingScanStater(IBuildingService buildingService) {
		super();
		setDaemon(true);
		setName("Building Update Stater");
		this.buildingService = buildingService;
	}
	
	
	public void run() {
		String [] areas = new String[0];
		
		ElectricHttpClient httpClient = new ElectricHttpClient();
		try {
			httpClient.perpare();
			AreaPage areaPage = new AreaPage(httpClient.getCurrentDocument());
			areas =  areaPage.getAreas();
		}  catch (RequestException e) {
			e.printStackTrace();
			return;
		}
		
		for(String area : areas) {
			BuildingScanCallable scanCallable = new BuildingScanCallable(area);
			Future<Building[]> result = executorService.submit(scanCallable);
			resultList.add(result);
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(Future<Building[]> result : resultList) {
			try {
				buildingList.addAll(Arrays.asList(result.get()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		buildingService.update(buildingList.toArray(new Building[0]));
	}
}

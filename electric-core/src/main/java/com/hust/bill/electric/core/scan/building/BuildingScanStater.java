package com.hust.bill.electric.core.scan.building;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.core.page.PageParseException;
import com.hust.bill.electric.service.IBuildingService;

public class BuildingScanStater extends Thread {

	private static Logger logger = LoggerFactory.getLogger(AreaBuildingScaner.class);
	
	private IBuildingService buildingService;
	
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	private List<Building> buildingList = new ArrayList<Building>(100);
	
	private List<Future<Building[]>> resultList = new ArrayList<Future<Building[]>>(10);  
	
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
			AreaPage areaPage = new AreaPage();
			areaPage.parse(httpClient.getCurrentDocument());
			areas =  areaPage.getAreas();
		} catch (RequestException e) {
			logger.error("get all area error", e);
			return;
		} catch (PageParseException e) {
			logger.error("AreaPage PageParseException, should not be occour", e);
		}
		
		for(String area : areas) {
			AreaBuildingScaner scanCallable = new AreaBuildingScaner(area);
			Future<Building[]> result = executorService.submit(scanCallable);
			resultList.add(result);
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("InterruptedException, should not be occour", e);
			}
		}
		
		for(Future<Building[]> result : resultList) {
			try {
				buildingList.addAll(Arrays.asList(result.get()));
			} catch (InterruptedException e) {
				logger.error("InterruptedException, should not be occour", e);
			} catch (ExecutionException e) {
				Throwable t = e.getCause();
				if(t instanceof PageParseException) {
					logger.error("PageParseException, should not be occour", e);
				} else {
					logger.error("ExecutionException occour", e);
				}
			}
		}
		
		buildingService.updateAll(buildingList.toArray(new Building[0]));
	}
}

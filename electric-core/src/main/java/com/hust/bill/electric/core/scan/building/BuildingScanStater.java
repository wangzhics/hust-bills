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
	
	private List<Future<AreaBuildingScanResult>> resultList = new ArrayList<Future<AreaBuildingScanResult>>(10);  
	
	public BuildingScanStater(IBuildingService buildingService) {
		setDaemon(true);
		setName("Building Scan Stater");
		this.buildingService = buildingService;
	}
	
	
	public void run() {
		
		ElectricHttpClient httpClient = new ElectricHttpClient();
		try {
			httpClient.perpare();
			AreaPage areaPage = new AreaPage();
			areaPage.parse(httpClient.getCurrentDocument());
			for(String area : areaPage.getAreas()) {
				AreaBuildingScaner scanCallable = new AreaBuildingScaner(area);
				Future<AreaBuildingScanResult> result = executorService.submit(scanCallable);
				resultList.add(result);
			}
		} catch (RequestException e) {
			logger.error("get all area error", e);
			return;
		} catch (PageParseException e) {
			logger.error("AreaPage PageParseException, should not be occour", e);
			return;
		}
		
		
		executorService.shutdown();
		while(!executorService.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("InterruptedException, should not be occour", e);
				return;
			}
		}
		
		try {
			for(Future<AreaBuildingScanResult> result : resultList) {
				AreaBuildingScanResult scanResult = result.get();
				buildingList.addAll(Arrays.asList(scanResult.getBuildings()));
			}
			buildingService.updateAll(buildingList.toArray(new Building[0]));
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
}

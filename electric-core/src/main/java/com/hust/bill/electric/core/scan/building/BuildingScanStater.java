package com.hust.bill.electric.core.scan.building;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.AreaPage;

public class BuildingScanStater implements Callable<BuildingScanResult>  {

	private static Logger logger = LoggerFactory.getLogger(BuildingScanStater.class);
	
	ElectricHttpClient httpClient = new ElectricHttpClient();
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private List<Future<AreaBuildingScanResult>> resultList = new ArrayList<Future<AreaBuildingScanResult>>(10);  
	private BuildingScanResult scanResult = new BuildingScanResult();
	
	public BuildingScanStater() {
	}

	@Override
	public BuildingScanResult call() throws Exception {
		
		logger.debug("perpare get areas");
		httpClient.perpare();
		AreaPage areaPage = new AreaPage();
		areaPage.parse(httpClient.getCurrentDocument());
		logger.debug("get areas success: {}", areaPage.getAreas().toString());
		
		logger.debug("perpare area building scaner");
		for(String area : areaPage.getAreas()) {
			scanResult.getUnSuccessAreaList().add(area);
			AreaBuildingScaner buildingScaner = new AreaBuildingScaner(area);
			Future<AreaBuildingScanResult> result = executorService.submit(buildingScaner);
			resultList.add(result);
		}
		logger.debug("perpare area building scaner finish");
		
		for(Future<AreaBuildingScanResult> result : resultList) {
			try {
				AreaBuildingScanResult areaScanResult = result.get();
				scanResult.getUnSuccessAreaList().remove(areaScanResult.getArea());
				scanResult.getSuccessAreaList().add(areaScanResult.getArea());
				scanResult.getBuildingList().addAll(areaScanResult.getBuildingList());
			} catch (ExecutionException e) {
				logger.error("area building scan failed", e);
				executorService.shutdownNow();
				return scanResult;
			}
		}
		return scanResult;
	}
}
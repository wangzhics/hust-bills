package com.hust.bill.electric.core.task.building;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.BuildingFloorPage;
import com.hust.bill.electric.core.page.BuildingNamePage;
import com.hust.bill.electric.service.IBuildingService;

public class BuildingScanThread extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(BuildingScanThread.class);
	
	private String area;
	private IBuildingService buildingService; 
	private List<Building> buildingList = new ArrayList<Building>(30);
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	public BuildingScanThread(String area, IBuildingService buildingService) {
		super();
		setDaemon(true);
		setName("Building[" + area + "] Scan Executor");
		this.area = area;
		this.buildingService = buildingService;
	}
	
	public void run() {
		try {
			httpClient.perpare();
			
			logger.info("Try get building names of area[" + area + "]");
			BuildingNameRequest buildingNameRequest = new BuildingNameRequest(area);
			httpClient.executeRequest(buildingNameRequest);
			BuildingNamePage buildingNamePage = new BuildingNamePage(httpClient.getCurrentDocument());
			logger.info("Get building names of area[" + area + "] Finish: " + buildingNamePage.getBuildingNames());
			
			for(String buildingName : buildingNamePage.getBuildingNames()) {
				logger.info("Try get building floor of building[" + buildingName + "]");
				BuildingFloorRequest floorRequest = new BuildingFloorRequest(area, buildingName);
				httpClient.executeRequest(floorRequest);
				BuildingFloorPage floorPage = new BuildingFloorPage(httpClient.getCurrentDocument());
				logger.info("Get building floor of building[" + buildingName + "] Finish: " + floorPage.getFloor());
				Building building = new Building(area, buildingName, floorPage.getFloor());
				buildingList.add(building);
			}
			
			finish();
		} catch (RequestException e) {
			e.printStackTrace();
		} 
	};
	

	private void finish() {
		Building[] buildings = buildingList.toArray(new Building[0]);
		logger.info("Update Area [" + area + "] Building [ " + buildings.toString() + "]");
		buildingService.update(buildings);
	}

}

package com.hust.bill.electric.core.task.building;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.BuildingFloorPage;
import com.hust.bill.electric.core.page.BuildingNamePage;

public class BuildingScanCallable implements Callable<Building[]> {
	
	private static Logger logger = LoggerFactory.getLogger(BuildingScanCallable.class);
	
	private String area;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	private List<Building> buildingList = new ArrayList<Building>(30);
	
	
	public BuildingScanCallable(String area) {
		this.area = area;
	}
	
	@Override
	public Building[] call() throws Exception {
		
		httpClient.perpare();
		
		logger.debug("try get area[{}]'s buildings", area);
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(area);
		httpClient.executeRequest(buildingNameRequest);
		BuildingNamePage buildingNamePage = new BuildingNamePage(httpClient.getCurrentDocument());
		logger.debug("area[{}]'s buildings are {}", area, buildingNamePage.getBuildingNames());
		
		for(String buildingName : buildingNamePage.getBuildingNames()) {
			logger.debug("try get building[{}-{}]'s floor", area, buildingName);
			BuildingFloorRequest floorRequest = new BuildingFloorRequest(area, buildingName);
			httpClient.executeRequest(floorRequest);
			BuildingFloorPage floorPage = new BuildingFloorPage(httpClient.getCurrentDocument());
			logger.info("building[{}-{}]'s floor is {}" ,area, buildingName, floorPage.getFloor());
			Building building = new Building(area, buildingName, floorPage.getFloor());
			buildingList.add(building);
		}
		Building[] buildings =  buildingList.toArray(new Building[0]);
		logger.info("area[{}]'s buildings are {}", area, buildings);
		return buildings;
	}

}

package com.hust.bill.electric.core.scan.building;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.BuildingFloorPage;
import com.hust.bill.electric.core.page.BuildingNamePage;
import com.hust.bill.electric.core.page.PageParseException;

public class AreaBuildingScaner implements Callable<AreaBuildingScanResult> {
	
	private static Logger logger = LoggerFactory.getLogger(AreaBuildingScaner.class);
	
	private String area;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	private List<Building> buildingList = new ArrayList<Building>(30);
	
	
	public AreaBuildingScaner(String area) {
		this.area = area;
	}
	
	@Override
	public AreaBuildingScanResult call() throws Exception {
		
		httpClient.perpare();
		for(String buildingName : getBuildingNames()) {
			int floor = getBuildingFloor(buildingName);
			Building building = new Building(area, buildingName, floor);
			buildingList.add(building);
		}
		Building[] buildings =  buildingList.toArray(new Building[0]);
		logger.info("area[{}]'s buildings are {}", area, buildings);
		return new AreaBuildingScanResult(buildings);
	}
	
	private String[] getBuildingNames()  throws RequestException, PageParseException {
		logger.debug("try get area[{}]'s building names", area);
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(area);
		httpClient.executeRequest(buildingNameRequest);
		BuildingNamePage buildingNamePage = new BuildingNamePage();
		buildingNamePage.parse(httpClient.getCurrentDocument());
		logger.debug("area[{}]'s building names are {}", area, buildingNamePage.getBuildingNames());
		return buildingNamePage.getBuildingNames();
	}
	
	private int getBuildingFloor(String buildingName) throws RequestException, PageParseException {
		logger.debug("try get building[{}-{}]'s floor", area, buildingName);
		BuildingFloorRequest floorRequest = new BuildingFloorRequest(area, buildingName);
		httpClient.executeRequest(floorRequest);
		BuildingFloorPage floorPage = new BuildingFloorPage();
		floorPage.parse(httpClient.getCurrentDocument());
		logger.debug("building[{}-{}]'s floor is {}" ,area, buildingName, floorPage.getFloor());
		return floorPage.getFloor();
	}

}

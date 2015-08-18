package com.hust.bill.electric.core.task.building;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.task.BasicSegmentConext;
import com.hust.bill.electric.service.IBuildingService;

public class UpdateSegmentConext extends BasicSegmentConext {

	private String area;
	private IBuildingService buildingService; 
	private List<Building> buildings = new ArrayList<Building>(30);
	
	public UpdateSegmentConext(String area, IBuildingService buildingService) {
		super();
		this.area = area;
		this.buildingService = buildingService;
	}

	public String getArea() {
		return area;
	}
	
	public List<Building> getBuildings() {
		return buildings;
	}
	
	public IBuildingService getBuildingService() {
		return buildingService;
	}
	
	
	
}

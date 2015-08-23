package com.hust.bill.electric.core.scan.building;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.Building;

public class AreaBuildingScanResult {

	private String area;
	private List<Building> buildingList = new ArrayList<Building>(20);

	public AreaBuildingScanResult() {
	}
	
	public String getArea() {
		return area;
	}
	
	public List<Building> getBuildingList() {
		return buildingList;
	}

	public void setArea(String area) {
		this.area = area;
	}

}

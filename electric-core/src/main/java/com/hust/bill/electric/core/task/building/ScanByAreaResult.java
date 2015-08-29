package com.hust.bill.electric.core.task.building;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.Building;

public class ScanByAreaResult {

	private String area;
	private List<Building> buildingList = new ArrayList<Building>(20);

	public ScanByAreaResult(String area) {
		this.area = area;
	}
	
	
	public String getArea() {
		return area;
	}
	
	public List<Building> getBuildingList() {
		return buildingList;
	}

}

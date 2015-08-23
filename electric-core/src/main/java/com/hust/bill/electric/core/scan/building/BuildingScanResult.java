package com.hust.bill.electric.core.scan.building;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.Building;

public class BuildingScanResult {

	private List<String> successAreaList = new ArrayList<String>(10);
	
	private List<String> unSuccessAreaList = new ArrayList<String>(10);
	
	private List<Building> buildingList = new ArrayList<Building>(100);
	 
	public List<String> getSuccessAreaList() {
		return successAreaList;
	}
	
	public List<String> getUnSuccessAreaList() {
		return unSuccessAreaList;
	}
	
	public List<Building> getBuildingList() {
		return buildingList;
	}
}

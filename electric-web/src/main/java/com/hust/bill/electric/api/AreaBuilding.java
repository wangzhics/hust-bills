package com.hust.bill.electric.api;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.Building;

public class AreaBuilding {

	private String area;
	private List<Building> buildings =  new ArrayList<Building>(30);
	
	public AreaBuilding(String area) {
		this.area = area;
	}
	
	public void addBuilding(Building building) {
		buildings.add(building);
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public void setBuildings(List<Building> buildings) {
		this.buildings = buildings;
	}
	
}

package com.hust.bill.electric.core.scan.building;

import com.hust.bill.electric.bean.Building;

public class AreaBuildingScanResult {

	private Building[] buildings;

	public AreaBuildingScanResult(Building[] buildings) {
		this.buildings = buildings;
	}
	
	public Building[] getBuildings() {
		return buildings;
	}
	
}

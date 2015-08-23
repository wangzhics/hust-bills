package com.hust.bill.electric.core.scan.room;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.Building;

public class RoomScanResult {

	private List<Building> successBuildingList = new ArrayList<Building>(50);
	
	private List<Building> unSuccessBuildingList = new ArrayList<Building>(50);
	
	public List<Building> getSuccessBuildingList() {
		return successBuildingList;
	}
	
	public List<Building> getUnSuccessBuildingList() {
		return unSuccessBuildingList;
	}
}

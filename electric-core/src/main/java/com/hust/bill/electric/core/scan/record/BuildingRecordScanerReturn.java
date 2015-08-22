package com.hust.bill.electric.core.scan.record;

import com.hust.bill.electric.bean.Room;

class BuildingRecordScanerReturn {

	private String buildingName;
	private int remainCount;
	private int chargeCount;
	private Room[] unSuccessRooms;
	
	public BuildingRecordScanerReturn(String buildingName, int remainCount, int chargeCount, Room[] unSuccessRooms) {
		this.buildingName = buildingName;
		this.remainCount = remainCount;
		this.chargeCount = chargeCount;
		this.unSuccessRooms = unSuccessRooms;
	}
	
	
	public String getBuildingName() {
		return buildingName;
	}
	
	public int getRemainCount() {
		return remainCount;
	}
	
	public int getChargeCount() {
		return chargeCount;
	}
	
	public Room[] getUnSuccessRooms() {
		return unSuccessRooms;
	}
	
	
}

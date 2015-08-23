package com.hust.bill.electric.core.scan.room;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;

public class BuildingRoomScanResult {

	private Building building;
	
	private Room[] rooms;

	public BuildingRoomScanResult(Building building, Room[] rooms) {
		this.building = building;
		this.rooms = rooms;
	}
	
	public Building getBuilding() {
		return building;
	}
	
	public Room[] getRooms() {
		return rooms;
	}
	
}

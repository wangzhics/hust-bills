package com.hust.bill.electric.core.scan.room;

import com.hust.bill.electric.bean.Room;

public class BuildingRoomScanResult {

	private Room[] rooms;

	public BuildingRoomScanResult(Room[] rooms) {
		this.rooms = rooms;
	}
	
	public Room[] getRooms() {
		return rooms;
	}
	
}

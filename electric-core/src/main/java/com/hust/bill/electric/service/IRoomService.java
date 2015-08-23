package com.hust.bill.electric.service;

import com.hust.bill.electric.bean.Room;

public interface IRoomService {

	public void updateByBuilding(String buildingName, Room[] rooms);
	
	public Room[] getByBuilding(String buildingName);
	
}

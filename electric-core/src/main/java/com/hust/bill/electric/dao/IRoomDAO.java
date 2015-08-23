package com.hust.bill.electric.dao;

import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Room;

@Repository(value="roomTempDAO")
public interface IRoomDAO {
	
	public void insert(Room rooms[]);
	
	public void delete(Room rooms[]);
	
	public void deleteByBuilding(String buildingName);
	
	public Room[] getByBuilding(String buildingName);

	
}

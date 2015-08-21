package com.hust.bill.electric.dao;

import com.hust.bill.electric.bean.Room;

public interface IRoomTempDAO {
	
	public void truncate(Room[] rooms);
	
	public void generate();
	
	public Room[] getAll();

	
}

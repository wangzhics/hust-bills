package com.hust.bill.electric.dao;

import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Room;

@Repository(value="roomTempDAO")
public interface IRoomTempDAO {
	
	public void truncate();
	
	public void generate();
	
	public Room[] getAll();

	
}

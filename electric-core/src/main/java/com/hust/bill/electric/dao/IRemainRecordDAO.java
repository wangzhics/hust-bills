package com.hust.bill.electric.dao;

import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;

@Repository(value="remainTempDAO")
public interface IRemainRecordDAO {

	public void insert(RemainRecord[] remainRecords);
	
	public void deleteByRooms(Room[] rooms);
	
	public RemainRecord[] getRoomLast(Room room);
	
}

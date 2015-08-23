package com.hust.bill.electric.dao;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.Room;

@Repository(value="chargeTempDAO")
public interface IChargeRecordDAO {

	public void insert(ChargeRecord[] chargeRecords);
	
	public void deleteByRooms(Room[] rooms);
	
	public ChargeRecord[] getRoomLast(Room room);
	
	public ChargeRecord[] getRoomGap(Room room, Date startDate, Date endDate);
	
}

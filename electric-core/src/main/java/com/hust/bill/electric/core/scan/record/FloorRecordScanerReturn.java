package com.hust.bill.electric.core.scan.record;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;

public class FloorRecordScanerReturn {

	private List<RemainRecord> remainList = new ArrayList<RemainRecord>(300);
	private List<ChargeRecord> chargeList = new ArrayList<ChargeRecord>(60);
	private List<Room> unSuccessRoomList = new ArrayList<Room>(40);
	
	public void addRemainRecord(RemainRecord remainRecord) {
		remainList.add(remainRecord);
	}
	
	public void addChargeRecord(ChargeRecord chargeRecord) {
		chargeList.add(chargeRecord);
	}
	
	public void addUnSuccessRoom(Room unSuccessRoom) {
		unSuccessRoomList.add(unSuccessRoom);
	}
	
	public List<RemainRecord> getRemainList() {
		return remainList;
	}
	
	public List<ChargeRecord> getChargeList() {
		return chargeList;
	}
	
	public List<Room> getUnSuccessRoomList() {
		return unSuccessRoomList;
	}
	
}

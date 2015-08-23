package com.hust.bill.electric.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.dao.IChargeRecordDAO;
import com.hust.bill.electric.dao.IRemainRecordDAO;
import com.hust.bill.electric.dao.IRoomDAO;
import com.hust.bill.electric.service.IRoomService;

@Service(value="roomService")
public class RoomService implements IRoomService {

	@Autowired
	private IRoomDAO roomDAO;

	@Autowired
	private IRemainRecordDAO remainRecordDAO;
	
	@Autowired
	private IChargeRecordDAO chargeRecordDAO;
	
	@Transactional
	public void updateByBuilding(String buildingName, Room[] rooms) {
		Set<Room> updateSet = new HashSet<Room>(rooms.length);
		Set<Room> insetSet = new HashSet<Room>(rooms.length);
		for(Room r : rooms) {
			updateSet.add(r);
			insetSet.add(r);
		}
		Room[] oldRooms = roomDAO.getByBuilding(buildingName);
		Set<Room> deleteSet = new HashSet<Room>(oldRooms.length);
		for(Room r : oldRooms) {
			deleteSet.add(r);
		}
		updateSet.containsAll(deleteSet);
		insetSet.retainAll(deleteSet);
		deleteSet.retainAll(updateSet);
		
		roomDAO.insert(insetSet.toArray(new Room[0]));
		
		Room[] deleteRooms = deleteSet.toArray(new Room[0]);
		chargeRecordDAO.deleteByRooms(deleteRooms);
		remainRecordDAO.deleteByRooms(deleteRooms);
		roomDAO.delete(deleteRooms);
	}

	public Room[] getByBuilding(String buildingName) {
		return roomDAO.getByBuilding(buildingName);
	}

}

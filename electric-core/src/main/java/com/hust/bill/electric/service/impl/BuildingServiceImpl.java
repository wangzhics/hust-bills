package com.hust.bill.electric.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.dao.IBuildingDAO;
import com.hust.bill.electric.dao.IChargeRecordDAO;
import com.hust.bill.electric.dao.IRemainRecordDAO;
import com.hust.bill.electric.dao.IRoomDAO;
import com.hust.bill.electric.service.IBuildingService;

@Service(value="buildingService")
public class BuildingServiceImpl implements IBuildingService{

	@Autowired
	private IBuildingDAO buildingDAO;
	
	@Autowired
	private IRoomDAO roomDAO;
	
	@Autowired
	private IRemainRecordDAO remainRecordDAO;
	
	@Autowired
	private IChargeRecordDAO chargeRecordDAO;
	
	@Override
	@Transactional
	public void updateAll(Building[] buildings) {
		Set<Building> updateSet = new HashSet<Building>(buildings.length);
		Set<Building> insetSet = new HashSet<Building>(buildings.length);
		for(Building b : buildings) {
			updateSet.add(b);
			insetSet.add(b);
		}
		Building[] oldBuilding = buildingDAO.getAll();
		Set<Building> deleteSet = new HashSet<Building>(oldBuilding.length);
		for(Building b : oldBuilding) {
			deleteSet.add(b);
		}
		updateSet.containsAll(deleteSet);
		insetSet.retainAll(deleteSet);
		deleteSet.retainAll(updateSet);
		
		buildingDAO.insert(insetSet.toArray(new Building[0]));
		buildingDAO.update(updateSet.toArray(new Building[0]));
		
		for(Building b : deleteSet) {
			Room[] rooms = roomDAO.getByBuilding(b.getName());
			chargeRecordDAO.deleteByRooms(rooms);
			remainRecordDAO.deleteByRooms(rooms);
			roomDAO.deleteByBuilding(b.getName());
		}
		buildingDAO.delete(deleteSet.toArray(new Building[0]));
	}

	@Override
	public Building[] getAll() {
		return buildingDAO.getAll();
	}

}

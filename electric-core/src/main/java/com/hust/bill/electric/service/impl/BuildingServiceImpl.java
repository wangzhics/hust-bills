package com.hust.bill.electric.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public Building[] getAll() {
		return buildingDAO.getAll();
	}

	@Override
	public void add(Building[] buildings) {
		buildingDAO.insert(buildings);
	}

	@Override
	public void delete(Building building) {
		Room[] rooms = roomDAO.getByBuilding(building.getName());
		chargeRecordDAO.deleteByRooms(rooms);
		remainRecordDAO.deleteByRooms(rooms);
		roomDAO.deleteByBuilding(building.getName());
		buildingDAO.delete(building);
	}

}

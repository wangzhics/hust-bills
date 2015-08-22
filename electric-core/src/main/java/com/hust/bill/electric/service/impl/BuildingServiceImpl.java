package com.hust.bill.electric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.dao.IBuildingDAO;
import com.hust.bill.electric.service.IBuildingService;

@Service(value="buildingService")
public class BuildingServiceImpl implements IBuildingService{

	@Autowired
	private IBuildingDAO buildingDAO;
	
	
	@Override
	@Transactional
	public void updateAll(Building[] newBuildings) {
		buildingDAO.truncate();
		buildingDAO.insert(newBuildings);
	}

	@Override
	public Building[] getAll() {
		return buildingDAO.getAll();
	}

}

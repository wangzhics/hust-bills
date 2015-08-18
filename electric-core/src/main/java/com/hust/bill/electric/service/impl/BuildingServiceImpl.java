package com.hust.bill.electric.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.dao.IBuildingDAO;
import com.hust.bill.electric.service.IBuildingService;

@Service(value="buildingService")
public class BuildingServiceImpl implements IBuildingService{

	private static Logger logger = LoggerFactory.getLogger(BuildingServiceImpl.class);
	
	@Autowired
	private IBuildingDAO buildingDAO;
	
	
	@Override
	public Building[] getByArea(String area) {
		return buildingDAO.getByArea(area);
	}
	
	@Override
	@Transactional
	public void update(Building[] newBuildings) {
		
		Set<String> newNameSet = new HashSet<>(newBuildings.length);
		for(Building b : newBuildings){
			newNameSet.add(b.getName());
		}
		Building[] oldBuildings = buildingDAO.getByNames(newNameSet.toArray(new String[0]));
		Set<String> oldNameSet = new HashSet<String>(oldBuildings.length);
		for(Building b : oldBuildings){
			oldNameSet.add(b.getName());
		}
		oldNameSet.retainAll(newNameSet);
		
		logger.info("Need Delete Buildings is " + oldNameSet.toString());
		if(oldNameSet.size() > 0) {
			buildingDAO.deleteByNames(oldNameSet.toArray(new String[0]));
		}
		logger.info("Need Insert Buildings is " + oldNameSet.toString());
		if(newBuildings.length > 0) {
			buildingDAO.insert(newBuildings);
		}
		
	}

	@Override
	public Building[] getAll() {
		return buildingDAO.getAll();
	}

}

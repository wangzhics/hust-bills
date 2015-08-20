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
	@Transactional
	public void add(Building[] newBuildings) {
		
		Set<Building> updateBuildingSet = new HashSet<Building>(newBuildings.length);
		Set<Building> insertBuildingSet = new HashSet<Building>(newBuildings.length);
		for(Building b : newBuildings){
			updateBuildingSet.add(b);
			insertBuildingSet.add(b);
		}
		
		Building[] oldBuildings = buildingDAO.getAll();
		Set<Building> oldBuildingSet = new HashSet<Building>(oldBuildings.length);
		for(Building b : oldBuildings){
			oldBuildingSet.add(b);
		}
		
		updateBuildingSet.retainAll(oldBuildingSet); 
		for(Building updateBuild : updateBuildingSet) {
			buildingDAO.update(updateBuild);//TODO use batch update
		}
		logger.info("update buildings[{}]", updateBuildingSet);
		
		insertBuildingSet.removeAll(oldBuildingSet);
		buildingDAO.insert(insertBuildingSet.toArray(new Building[0]));
		logger.info("insert buildings[{}]", insertBuildingSet);
	}

	@Override
	public Building[] getAll() {
		return buildingDAO.getAll();
	}

}

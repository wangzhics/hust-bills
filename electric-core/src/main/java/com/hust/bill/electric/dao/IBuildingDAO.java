package com.hust.bill.electric.dao;


import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Building;

@Repository(value="buildingDAO")
public interface IBuildingDAO {

	public void insert(Building[] buildings);
	
	public void delete(Building buildings);
	
	public Building[] getAll();
	
}

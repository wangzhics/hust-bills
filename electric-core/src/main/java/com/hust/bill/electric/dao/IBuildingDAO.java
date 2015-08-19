package com.hust.bill.electric.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Building;

@Repository(value="buildingDAO")
public interface IBuildingDAO {

	public void insert(Building[] buildings);
	
	//public void update(Building[] buildings);
	
	public void deleteByNames(@Param("names")String[] names);
	
	public Building[] getByNames(@Param("names")String[] names);
	
	public Building[] getByArea(String area);
	
	public Building[] getAll();
	
}

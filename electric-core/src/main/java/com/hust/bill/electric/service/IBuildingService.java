package com.hust.bill.electric.service;

import com.hust.bill.electric.bean.Building;

public interface IBuildingService {
	
	public void add(Building[] buildings);
	
	public void delete(Building building);
	
	public Building[] getAll();
	
}

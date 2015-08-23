package com.hust.bill.electric.service;

import com.hust.bill.electric.bean.Building;

public interface IBuildingService {
	
	public void updateAll(Building[] buildings);
	
	public Building[] getAll();
	
}

package com.hust.bill.electric.service;

import com.hust.bill.electric.bean.Building;

public interface IBuildingService {
	
	public Building[] getByArea(String area);

	public void update(Building[] newBuildings);
	
	public Building[] getAll();
	
}

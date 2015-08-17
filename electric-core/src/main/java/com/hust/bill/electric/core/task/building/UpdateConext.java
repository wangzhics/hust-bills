package com.hust.bill.electric.core.task.building;

import java.util.ArrayList;
import java.util.List;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.task.ChainConext;

public class UpdateConext extends ChainConext {

	private String area;
	private List<Building> buildings = new ArrayList<Building>(30);
	
	public String getArea() {
		return area;
	}
	
	public void setArea(String area) {
		this.area = area;
	}
	
	public List<Building> getBuildings() {
		return buildings;
	}
	
	
	
	
}

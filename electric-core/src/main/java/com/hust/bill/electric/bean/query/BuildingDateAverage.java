package com.hust.bill.electric.bean.query;

import java.util.Date;

public class BuildingDateAverage {

	private String buildingName;
	private Date date;
	private float average;
	
	
	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getAverage() {
		return average;
	}

	public void setAverage(float average) {
		this.average = average;
	}
}

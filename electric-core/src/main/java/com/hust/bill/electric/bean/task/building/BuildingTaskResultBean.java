package com.hust.bill.electric.bean.task.building;

import java.math.BigInteger;

public class BuildingTaskResultBean {

	private BigInteger id;
	private BigInteger taskID;
	private String areaName;
	private String buildingName;
	private int buildingFloor;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public BigInteger getTaskID() {
		return taskID;
	}
	public void setTaskID(BigInteger taskID) {
		this.taskID = taskID;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public int getBuildingFloor() {
		return buildingFloor;
	}
	public void setBuildingFloor(int buildingFloor) {
		this.buildingFloor = buildingFloor;
	}
	
}

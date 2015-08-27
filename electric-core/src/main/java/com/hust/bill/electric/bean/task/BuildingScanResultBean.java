package com.hust.bill.electric.bean.task;

import java.math.BigInteger;

public class BuildingScanResultBean {

	private BigInteger scanID;
	private BigInteger id;
	private String areaName;
	private String buildingName;
	private String buildingFloor;
	public BigInteger getScanID() {
		return scanID;
	}
	public void setScanID(BigInteger scanID) {
		this.scanID = scanID;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
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
	public String getBuildingFloor() {
		return buildingFloor;
	}
	public void setBuildingFloor(String buildingFloor) {
		this.buildingFloor = buildingFloor;
	}
	
}

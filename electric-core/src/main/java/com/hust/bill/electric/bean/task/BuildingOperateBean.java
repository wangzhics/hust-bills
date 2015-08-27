package com.hust.bill.electric.bean.task;

import java.math.BigInteger;
import java.util.Date;

import com.hust.bill.electric.bean.Building;

public class BuildingOperateBean {
	private BigInteger scanID;
	private String scanName;
	private BigInteger resultID;
	private String areaName;
	private String buildingName;
	private int buildingFloor;
	private Operation operate;
	private Date timestmp;
	
	public Building newBuilding() {
		return new Building(areaName, buildingName, buildingFloor);
	}
	
	public BigInteger getScanID() {
		return scanID;
	}
	public void setScanID(BigInteger scanID) {
		this.scanID = scanID;
	}
	public String getScanName() {
		return scanName;
	}
	public void setScanName(String scanName) {
		this.scanName = scanName;
	}
	public BigInteger getResultID() {
		return resultID;
	}
	public void setResultID(BigInteger resultID) {
		this.resultID = resultID;
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
	public Operation getOperate() {
		return operate;
	}
	public void setOperate(Operation operate) {
		this.operate = operate;
	}
	public Date getTimestmp() {
		return timestmp;
	}
	public void setTimestmp(Date timestmp) {
		this.timestmp = timestmp;
	}
	
}

package com.hust.bill.electric.bean.task.building;

import java.math.BigInteger;
import java.util.Date;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.Operation;

public class BuildingOperateBean {
	private BigInteger taskID;
	private String taskName;
	private BigInteger resultID;
	private String areaName;
	private String buildingName;
	private int buildingFloor;
	private Operation operate;
	private Date timestamp;
	
	public static BuildingOperateBean newOperateBean(BuildingTaskBean taskBean, BuildingTaskResultBean resultBean, Operation operation) {
		BuildingOperateBean operateBean = new BuildingOperateBean();
		operateBean.taskID = taskBean.getId();
		operateBean.taskName = taskBean.getName();
		operateBean.resultID = resultBean.getId();
		operateBean.areaName = resultBean.getAreaName();
		operateBean.buildingName = resultBean.getBuildingName(); 
		operateBean.buildingFloor = resultBean.getBuildingFloor();
		operateBean.operate = operation;
		return operateBean;
	}
	
	public Building newBuilding() {
		return new Building(areaName, buildingName, buildingFloor);
	}
	public BigInteger getTaskID() {
		return taskID;
	}
	public void setTaskID(BigInteger taskID) {
		this.taskID = taskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
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
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}

package com.hust.bill.electric.bean.task.record;

import java.math.BigInteger;
import java.util.Date;

public class RecordTaskResultBean {
	private BigInteger id;
	private BigInteger taskID;
	private String buildingName;
	private int remainCount;
	private int chargeCount;
	private Date timestamp;
	
	public RecordTaskResultBean(BigInteger taskID, String buildingName, int remainCount, int chargeCount) {
		this.taskID = taskID;
		this.buildingName = buildingName;
		this.remainCount = remainCount;
		this.chargeCount = chargeCount;
	}
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
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public int getRemainCount() {
		return remainCount;
	}
	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}
	public int getChargeCount() {
		return chargeCount;
	}
	public void setChargeCount(int chargeCount) {
		this.chargeCount = chargeCount;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}

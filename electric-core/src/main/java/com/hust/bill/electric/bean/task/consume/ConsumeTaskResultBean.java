package com.hust.bill.electric.bean.task.consume;

import java.math.BigInteger;
import java.util.Date;

public class ConsumeTaskResultBean {
	
	private BigInteger id;
	private BigInteger taskID;
	private String buildingName;
	private int consumeCount;
	private Date timestamp;
	
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
	public int getConsumeCount() {
		return consumeCount;
	}
	public void setConsumeCount(int consumeCount) {
		this.consumeCount = consumeCount;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
}

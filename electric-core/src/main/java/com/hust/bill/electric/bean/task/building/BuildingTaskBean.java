package com.hust.bill.electric.bean.task.building;

import java.math.BigInteger;
import java.util.Date;

import com.hust.bill.electric.bean.task.TaskStatus;

public class BuildingTaskBean {

	private BigInteger id;
	private String name;
	private Date startTime;
	private Date endTime;
	private int resultCount;
	private TaskStatus status;
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getResultCount() {
		return resultCount;
	}
	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}
	public TaskStatus getStatus() {
		return status;
	}
	public void setStatus(TaskStatus status) {
		this.status = status;
	}
}
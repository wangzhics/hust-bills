package com.hust.bill.electric.bean.task;

import java.math.BigInteger;
import java.util.Date;

public class BuildingScanTaskBean {

	private BigInteger id;
	private String name;
	private Date starTime;
	private Date endTime;
	private int resultCount;
	private int status;
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
	public Date getStarTime() {
		return starTime;
	}
	public void setStarTime(Date starTime) {
		this.starTime = starTime;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
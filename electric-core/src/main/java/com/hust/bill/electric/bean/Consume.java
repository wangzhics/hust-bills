package com.hust.bill.electric.bean;

import java.util.Date;

public class Consume {
	
	private String buildingName;
	
	private String roomName;
	
	private Date date;
	
	private float consume;

	public Consume() {
	}
	
	public Consume(String buildingName, String roomName, Date date, float consume) {
		super();
		this.buildingName = buildingName;
		this.roomName = roomName;
		this.date = date;
		this.consume = consume;
	}



	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getConsume() {
		return consume;
	}

	public void setConsume(float consume) {
		this.consume = consume;
	}
	
}

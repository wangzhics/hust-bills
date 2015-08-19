package com.hust.bill.electric.bean;

import java.util.Date;

public class ChargeRecord {

	private String buildingName;
	
	private String roomName;
	
	private Date dateTime;
	
	private float chargePower;
	
	private float chargeMoney;
	
	public ChargeRecord() {
	}
	
	public ChargeRecord(String buildingName, String roomName, Date dateTime, float chargePower, float chargeMoney) {
		super();
		this.buildingName = buildingName;
		this.roomName = roomName;
		this.dateTime = dateTime;
		this.chargePower = chargePower;
		this.chargeMoney = chargeMoney;
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

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public float getChargePower() {
		return chargePower;
	}

	public void setChargePower(float chargePower) {
		this.chargePower = chargePower;
	}

	public float getChargeMoney() {
		return chargeMoney;
	}

	public void setChargeMoney(float chargeMoney) {
		this.chargeMoney = chargeMoney;
	}
	
}

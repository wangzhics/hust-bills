package com.hust.bill.electric.bean;

import java.text.DecimalFormat;
import java.util.Date;

public class RemainRecord {
	
	private final static DecimalFormat df = new DecimalFormat("00");
	
	
	private String buildingName;
	
	private String roomName;
	
	private Date dateTime;
	
	private float remain;

	public RemainRecord() {
		
	}
	
	public RemainRecord(String buildingName, String roomName, Date dateTime, float remain) {
		this.buildingName = buildingName;
		this.roomName = roomName;
		this.dateTime = dateTime;
		this.remain = remain;
	}
	
	public RemainRecord(String buildingName, int floor, int room, Date dateTime, float remain) {
		this.buildingName = buildingName;
		this.roomName = getRoomName(floor, room);
		this.dateTime = dateTime;
		this.remain = remain;
	}
	
	public static String getRoomName(int floor, int room) {
		return String.valueOf(floor) + df.format(room);
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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

	public void setRoomName(String roomStr) {
		this.roomName = roomStr;
	}
	
	public float getRemain() {
		return remain;
	}
	
	public void setRemain(float remain) {
		this.remain = remain;
	}

	@Override
	public String toString() {
		return "RemainRecord [buildingName=" + buildingName + ", roomStr=" + roomName + ", dateTime=" + dateTime
				+ ", remain=" + remain + "]";
	}
}

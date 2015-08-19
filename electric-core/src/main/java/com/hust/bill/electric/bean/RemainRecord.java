package com.hust.bill.electric.bean;

import java.text.DecimalFormat;
import java.util.Date;

public class RemainRecord {
	
	private final static DecimalFormat df = new DecimalFormat("00");
	
	
	private String buildingName;
	
	private String roomStr;
	
	private Date dateTime;
	
	private float remain;

	public RemainRecord() {
		
	}
	
	public RemainRecord(String buildingName, String roomStr, Date dateTime, float remain) {
		this.buildingName = buildingName;
		this.roomStr = roomStr;
		this.dateTime = dateTime;
		this.remain = remain;
	}
	
	public RemainRecord(String buildingName, int floor, int room, Date dateTime, float remain) {
		this.buildingName = buildingName;
		this.roomStr = getRoomStr(floor, room);
		this.dateTime = dateTime;
		this.remain = remain;
	}
	
	public static String getRoomStr(int floor, int room) {
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

	public String getRoomStr() {
		return roomStr;
	}

	public void setRoomStr(String roomStr) {
		this.roomStr = roomStr;
	}
	
	public float getRemain() {
		return remain;
	}
	
	public void setRemain(float remain) {
		this.remain = remain;
	}

	@Override
	public String toString() {
		return "RemainRecord [buildingName=" + buildingName + ", roomStr=" + roomStr + ", dateTime=" + dateTime
				+ ", remain=" + remain + "]";
	}
}

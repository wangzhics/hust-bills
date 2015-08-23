package com.hust.bill.electric.bean;

import java.text.DecimalFormat;

public class Room {

	private final static DecimalFormat df = new DecimalFormat("00");
	
	private String buildingName;
	
	private String roomName;
	
	private int roomFloor;
	
	private int roomNO;

	public Room() {
		
	}

	public Room(String buildingName, int roomFloor, int roomNO) {
		this.buildingName = buildingName;
		this.roomFloor = roomFloor;
		this.roomNO = roomNO;
		this.roomName = getRoomName(roomFloor, roomNO);
	}

	public static String getRoomName(int floor, int room) {
		return String.valueOf(floor) + df.format(room);
	}

	public int getRoomFloor() {
		return roomFloor;
	}
	
	public int getRoomNO() {
		return roomNO;
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

	@Override
	public String toString() {
		return "Room [buildingName=" + buildingName + ", roomName=" + roomName + "]";
	}
}

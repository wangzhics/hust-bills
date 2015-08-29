package com.hust.bill.electric.bean.task.room;

import java.math.BigInteger;

public class RoomTaskResultBean {
	private BigInteger id;
	private BigInteger taskID;
	private String buildingName;
	private String roomName;
	private int roomFloor;
	private int roomNO;
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
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public int getRoomFloor() {
		return roomFloor;
	}
	public void setRoomFloor(int roomFloor) {
		this.roomFloor = roomFloor;
	}
	public int getRoomNO() {
		return roomNO;
	}
	public void setRoomNO(int roomNO) {
		this.roomNO = roomNO;
	}
}

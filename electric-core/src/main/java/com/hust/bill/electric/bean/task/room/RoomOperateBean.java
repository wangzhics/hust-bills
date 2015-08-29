package com.hust.bill.electric.bean.task.room;

import java.math.BigInteger;
import java.util.Date;

import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.Operation;

public class RoomOperateBean {

	private BigInteger taskID;
	private String taskName;
	private String buildingName;
	private BigInteger resultID;
	private String roomName;
	private int roomFloor;
	private int roomNO;
	private Operation operate;
	private Date timestamp;
	
	public Room newRoom() {
		Room room = new Room();
		room.setBuildingName(buildingName);
		room.setRoomName(roomName);
		room.setRoomFloor(roomFloor);
		room.setRoomNO(roomNO);
		return room;
	}
	
	public BigInteger getTaskID() {
		return taskID;
	}
	public void setTaskID(BigInteger taskID) {
		this.taskID = taskID;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getBuildingName() {
		return buildingName;
	}
	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}
	public BigInteger getResultID() {
		return resultID;
	}
	public void setResultID(BigInteger resultID) {
		this.resultID = resultID;
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
	public Operation getOperate() {
		return operate;
	}
	public void setOperate(Operation operate) {
		this.operate = operate;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}

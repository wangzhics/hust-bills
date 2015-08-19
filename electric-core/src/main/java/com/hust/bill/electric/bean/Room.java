package com.hust.bill.electric.bean;

public class Room {

	private String buildingName;
	
	private String name;
	
	private int floor;

	public Room() {
		
	}
	
	public Room(String buildingName, String name, int floor) {
		super();
		this.buildingName = buildingName;
		this.name = name;
		this.floor = floor;
	}


	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}
	
}

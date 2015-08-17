package com.hust.bill.electric.bean;

public class Building {

	private String area;
	private String name;
	private int floor;
	
	public Building(String area, String name, int floor) {
		super();
		this.area = area;
		this.name = name;
		this.floor = floor;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
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

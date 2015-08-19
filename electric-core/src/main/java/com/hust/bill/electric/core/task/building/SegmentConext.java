package com.hust.bill.electric.core.task.building;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.service.IBuildingService;

public class SegmentConext {

	private String area;
	private IBuildingService buildingService; 
	private List<Building> buildings = new ArrayList<Building>(30);
	
	private Queue<ITaskSegment> taskSegments = new LinkedList<>();
	private ElectricHttpClient httpClient = new ElectricHttpClient();

	public SegmentConext(String area, IBuildingService buildingService) {
		this.area = area;
		this.buildingService = buildingService;
	}
	public Queue<ITaskSegment> getTaskSegments() {
		return taskSegments;
	}

	public void setTaskSegments(Queue<ITaskSegment> taskSegments) {
		this.taskSegments = taskSegments;
	}

	public ElectricHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(ElectricHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public String getArea() {
		return area;
	}
	
	public List<Building> getBuildings() {
		return buildings;
	}
	
	public IBuildingService getBuildingService() {
		return buildingService;
	}
	
	
	
}

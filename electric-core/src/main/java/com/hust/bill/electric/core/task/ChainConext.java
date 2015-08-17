package com.hust.bill.electric.core.task;

import java.util.LinkedList;
import java.util.Queue;

import com.hust.bill.electric.core.http.ElectricHttpClient;

public class ChainConext {
	
	private Queue<ITaskSegment> taskSegments = new LinkedList<>();
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();

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
	
	
}

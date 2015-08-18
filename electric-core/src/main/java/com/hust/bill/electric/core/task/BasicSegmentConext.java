package com.hust.bill.electric.core.task;

import java.util.LinkedList;
import java.util.Queue;

import com.hust.bill.electric.core.http.ElectricHttpClient;

public class BasicSegmentConext {
	
	private Queue<BasicTaskSegment> taskSegments = new LinkedList<>();
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();

	public Queue<BasicTaskSegment> getTaskSegments() {
		return taskSegments;
	}

	public void setTaskSegments(Queue<BasicTaskSegment> taskSegments) {
		this.taskSegments = taskSegments;
	}

	public ElectricHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(ElectricHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	
}

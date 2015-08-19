package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.service.IBuildingService;

public class SegmentExecutor extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(SegmentExecutor.class);
	
	private SegmentConext segmentConext;
	
	public SegmentExecutor(SegmentConext segmentConext) {
		super();
		setDaemon(true);
		setName("Building[" + segmentConext.getArea() + "] Scan Executor Thread");
		this.segmentConext = segmentConext;
	}
	
	
	public void run() {
		perpare();
		ITaskSegment currentSegment = segmentConext.getTaskSegments().poll();
		while(currentSegment != null){
			currentSegment.execute(segmentConext);
			currentSegment = segmentConext.getTaskSegments().poll();
		}
		finish();
	};
	

	private void finish() {
		Building[] buildings = segmentConext.getBuildings().toArray(new Building[0]);
		IBuildingService buildingService =  segmentConext.getBuildingService();
		logger.info("Update Building[" + segmentConext.getArea() + "] Is " + buildings.toString());
		buildingService.update(buildings);
	}

	private void perpare() {
		try {
			segmentConext.getHttpClient().perpare();
			BuildingSegment buildingSegment = new BuildingSegment();
			segmentConext.getTaskSegments().add(buildingSegment);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

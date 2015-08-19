package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.BuildingFloorPage;

public class FloorSegment implements ITaskSegment {

	private String building;
	
	public FloorSegment(String building) {
		this.building = building;
	}
	
	@Override
	public void execute(SegmentConext segmentConext) {
		BuildingFloorRequest floorRequest = new BuildingFloorRequest(segmentConext.getArea(), building);
		ElectricHttpClient httpClient = segmentConext.getHttpClient();
		try {
			httpClient.executeRequest(floorRequest);
			BuildingFloorPage floorPage = new BuildingFloorPage();
			floorPage.updateAttributes(httpClient.getCurrentDocument());
			segmentConext.getBuildings().add(new Building(segmentConext.getArea(), building, floorPage.getFloor()));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

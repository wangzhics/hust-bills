package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.BuildingFloorPage;
import com.hust.bill.electric.core.task.BasicSegmentConext;
import com.hust.bill.electric.core.task.BasicTaskSegment;

public class FloorSegment extends BasicTaskSegment {

	private String building;
	
	public FloorSegment(BasicSegmentConext segmentConext, String building) {
		super(segmentConext);
		this.building = building;
	}
	
	@Override
	public UpdateSegmentConext getSegmentConext() {
		return (UpdateSegmentConext) super.getSegmentConext();
	}

	@Override
	public void execute() {
		BuildingFloorRequest floorRequest = new BuildingFloorRequest(getSegmentConext().getArea(), building);
		ElectricHttpClient httpClient = getSegmentConext().getHttpClient();
		try {
			httpClient.executeRequest(floorRequest);
			BuildingFloorPage floorPage = new BuildingFloorPage();
			floorPage.updateAttributes(httpClient.getCurrentDocument());
			getSegmentConext().getBuildings().add(new Building(getSegmentConext().getArea(), building, floorPage.getFloor()));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

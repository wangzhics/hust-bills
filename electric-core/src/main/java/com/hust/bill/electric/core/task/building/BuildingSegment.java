package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.BuildingNamePage;
import com.hust.bill.electric.core.task.BasicSegmentConext;
import com.hust.bill.electric.core.task.BasicTaskSegment;

public class BuildingSegment extends BasicTaskSegment {

	
	public BuildingSegment(BasicSegmentConext segmentConext) {
		super(segmentConext);
	}

	@Override
	public UpdateSegmentConext getSegmentConext() {
		return (UpdateSegmentConext) super.getSegmentConext();
	}
	
	@Override
	public void execute() {
		BuildingNameRequest request = new BuildingNameRequest(getSegmentConext().getArea());
		try {
			ElectricHttpClient httpClient = getSegmentConext().getHttpClient();
			httpClient.executeRequest(request);
			BuildingNamePage buildingNamePage = new BuildingNamePage();
			buildingNamePage.updateAttributes(httpClient.getCurrentDocument());
			for(String buildingName : buildingNamePage.getBuildingNames()) {
				FloorSegment floorSegment = new FloorSegment(getSegmentConext(), buildingName);
				getSegmentConext().getTaskSegments().add(floorSegment);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

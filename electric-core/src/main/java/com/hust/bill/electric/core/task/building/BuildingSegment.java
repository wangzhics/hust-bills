package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.BuildingNamePage;

public class BuildingSegment implements ITaskSegment {
	
	@Override
	public void execute(SegmentConext segmentConext) {
		BuildingNameRequest request = new BuildingNameRequest(segmentConext.getArea());
		try {
			ElectricHttpClient httpClient = segmentConext.getHttpClient();
			httpClient.executeRequest(request);
			BuildingNamePage buildingNamePage = new BuildingNamePage();
			buildingNamePage.updateAttributes(httpClient.getCurrentDocument());
			for(String buildingName : buildingNamePage.getBuildingNames()) {
				FloorSegment floorSegment = new FloorSegment(buildingName);
				segmentConext.getTaskSegments().add(floorSegment);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

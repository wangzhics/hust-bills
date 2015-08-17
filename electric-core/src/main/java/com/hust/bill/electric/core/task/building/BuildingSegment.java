package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.BuildingNamePage;
import com.hust.bill.electric.core.task.ChainConext;
import com.hust.bill.electric.core.task.ITaskSegment;

public class BuildingSegment implements ITaskSegment {

	private String area;
	
	public BuildingSegment(String area) {
		this.area = area;
	}
	
	@Override
	public void execute(ChainConext chainConext) {
		BuildingNameRequest request = new BuildingNameRequest(area);
		try {
			ElectricHttpClient httpClient = chainConext.getHttpClient();
			httpClient.executeRequest(request);
			BuildingNamePage buildingNamePage = new BuildingNamePage();
			buildingNamePage.updateAttributes(httpClient.getCurrentDocument());
			for(String buildingName : buildingNamePage.getBuildingNames()) {
				FloorSegment floorSegment = new FloorSegment(area, buildingName);
				chainConext.getTaskSegments().add(floorSegment);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

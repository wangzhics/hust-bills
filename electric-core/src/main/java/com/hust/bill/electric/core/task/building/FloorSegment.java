package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.BuildingFloorPage;
import com.hust.bill.electric.core.task.ChainConext;
import com.hust.bill.electric.core.task.ITaskSegment;

public class FloorSegment implements ITaskSegment  {

	private String area;
	private String building;
	
	public FloorSegment(String area, String building) {
		this.area = area;
		this.building = building;
	}

	@Override
	public void execute(ChainConext chainConext) {
		BuildingFloorRequest floorRequest = new BuildingFloorRequest(area, building);
		ElectricHttpClient httpClient = chainConext.getHttpClient();
		try {
			httpClient.executeRequest(floorRequest);
			BuildingFloorPage floorPage = new BuildingFloorPage();
			floorPage.updateAttributes(httpClient.getCurrentDocument());
			UpdateConext updateConext = (UpdateConext) chainConext;
			updateConext.getBuildings().add(new Building(area, building, floorPage.getFloor()));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

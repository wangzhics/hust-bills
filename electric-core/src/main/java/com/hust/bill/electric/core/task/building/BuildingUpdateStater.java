package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.service.IBuildingService;

public class BuildingUpdateStater extends Thread {

	private IBuildingService buildingService;
	
	public BuildingUpdateStater(IBuildingService buildingService) {
		super();
		setDaemon(true);
		setName("Building Update Stater");
		this.buildingService = buildingService;
	}
	
	@Override
	public void run() {
		ElectricHttpClient httpClient = new ElectricHttpClient();
		try {
			httpClient.perpare();
			AreaPage areaPage = new AreaPage(httpClient.getCurrentDocument());
			for(String area : areaPage.getAreas()) {
				BuildingUpdateThread updateExecutor = new BuildingUpdateThread(area, buildingService);
				updateExecutor.start();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.service.IBuildingService;

public class UpdateStater extends Thread {

	private IBuildingService buildingService;
	
	public UpdateStater(IBuildingService buildingService) {
		super();
		setDaemon(true);
		setName("Building Update Stater Thread");
		this.buildingService = buildingService;
	}
	
	@Override
	public void run() {
		ElectricHttpClient httpClient = new ElectricHttpClient();
		try {
			httpClient.perpare();
			AreaPage areaPage = new AreaPage();
			areaPage.updateAttributes(httpClient.getCurrentDocument());
			for(String area : areaPage.getAreas()) {
				SegmentConext updateConext = new SegmentConext(area, buildingService);
				SegmentExecutor updateExecutor = new SegmentExecutor(updateConext);
				updateExecutor.start();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

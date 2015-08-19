package com.hust.bill.electric.core.task.building;


import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.service.IBuildingService;

public class BuildingScanStater extends Thread {

	private IBuildingService buildingService;
	
	public BuildingScanStater(IBuildingService buildingService) {
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
				BuildingScanThread updateExecutor = new BuildingScanThread(area, buildingService);
				updateExecutor.start();
			}
		} catch (RequestException e) {
			e.printStackTrace();
		}
	}
}

package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.AreaPage;

public class UpdateStater extends Thread {

	@Override
	public void run() {
		ElectricHttpClient httpClient = new ElectricHttpClient();
		try {
			httpClient.perpare();
			AreaPage areaPage = new AreaPage();
			areaPage.updateAttributes(httpClient.getCurrentDocument());
			for(String area : areaPage.getAreas()) {
				UpdateConext updateConext = new UpdateConext();
				UpdateExecutor updateExecutor = new UpdateExecutor(updateConext, area);
				updateExecutor.start();
				break;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

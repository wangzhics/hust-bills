package com.hust.bill.electric.core.scan.record;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.PageParseException;
import com.hust.bill.electric.core.page.RecordChargeLine;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.page.RecordRemainLine;
import com.hust.bill.electric.service.IRecordService;


public class BuildingRecordScaner implements Callable<BuildingRecordScanerReturn>  {

	private static Logger logger = LoggerFactory.getLogger(BuildingRecordScaner.class);
	
	private Building building;
	private IRecordService recordService;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private List<RemainRecord> remainList = new ArrayList<RemainRecord>(2000);
	private List<ChargeRecord> chargeList = new ArrayList<ChargeRecord>(200);
	
	public BuildingRecordScaner(Building building, IRecordService recordService) {
		this.building = building;
		this.recordService = recordService;
	}
	
	
	@Override
	public BuildingRecordScanerReturn call() throws Exception {
		
		perpare();
		
		int continueFalse = 0;
		for(int floor = 1; floor <= building.getFloor(); floor++) {
			for(int roomNO = 1; roomNO < 100; roomNO ++) {
				String roomName = Room.getRoomName(floor, roomNO);
				RecordPage recordPage = getRecordPage(floor, roomNO);
				if(recordPage.hasRecord()) {
					addRoomRecords(recordPage);
				} else {
					continueFalse ++;
				} 
				if(continueFalse >  3) {
					logger.info("floor[{}-{}] max room is {}", building.getName(), floor, (roomNO - continueFalse));
					break;
				}
			}
		}
		logger.info("buildings[{}] remain count is [{}], charge cout is[{}]", building.getName(), remainList.size());
		
		saveRecords();
		
		return new BuildingRecordScanerReturn(building.getName(), remainList.size(), chargeList.size());
		
		
	}
	
	
	private void perpare() throws RequestException {
		logger.debug("building[{}] record scaner http client perpare start", building.getName());
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("building[{}] record scaner http client perpare finish", building.getName());
	}
	
	private RecordPage getRecordPage(int floor, int roomNO) throws RequestException, PageParseException {
		String roomName = Room.getRoomName(floor, roomNO);
		logger.debug("room[{}-{}] record page get start", building.getName(), roomName);
		RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
		httpClient.executeRequest(recordRequest);
		RecordPage recordPage =  new RecordPage();
		recordPage.parse(httpClient.getCurrentDocument());
		if(recordPage.hasRecord()) {
			logger.debug("room[{}-{}] record page get success: remain are[{}], charge are[{}]", building.getName(), roomName, recordPage.getRemainLines(), recordPage.getChargeLines());
		} 
		return recordPage;
	}
	
	private void addRoomRecords(RecordPage recordPage) {
		for(RecordRemainLine remainLine : recordPage.getRemainLines()) {
			RemainRecord remainRecord = new RemainRecord(recordPage.getBuildingName(), recordPage.getRoomName(), remainLine.getDate(), remainLine.getRemain());
			remainList.add(remainRecord);
		}
		for(RecordChargeLine chargeLine : recordPage.getChargeLines()) {
			ChargeRecord chargeRecord = new ChargeRecord(recordPage.getBuildingName(), recordPage.getRoomName(), chargeLine.getDate(), chargeLine.getPower(), chargeLine.getMoney());
			chargeList.add(chargeRecord);
		}
	}
	
	private void saveRecords() {
		recordService.addTempRemains(remainList.toArray(new RemainRecord[0]));
		recordService.addTempCharges(chargeList.toArray(new ChargeRecord[0]));
	}

}

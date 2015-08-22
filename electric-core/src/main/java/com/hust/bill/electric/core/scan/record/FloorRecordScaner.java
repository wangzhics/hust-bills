package com.hust.bill.electric.core.scan.record;

import java.util.concurrent.Callable;

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

public class FloorRecordScaner implements Callable<FloorRecordScanerReturn> {

	private static Logger logger = LoggerFactory.getLogger(FloorRecordScaner.class);
	
	private int floor;
	private Building building;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	private FloorRecordScanerReturn scanerReturn = new FloorRecordScanerReturn();
	
	public FloorRecordScaner(int floor, Building building) throws Exception {
		this.floor = floor;
		this.building = building;
	}
	
	@Override
	public FloorRecordScanerReturn call() throws Exception {
		
		try {
			perpare();
		} catch (RequestException e) {
			logger.error("floor[{}-{}] record scaner http client perpare error", building.getName(), floor, e);
			return scanerReturn;
		}
		
		int continueFalse = 0;
		for(int room = 1; room < 100; room ++) {
			String roomName = Room.getRoomName(floor, room);
			try {
				RecordPage recordPage = getRecordPage(floor, room);
				if(recordPage.isErrorPage()) {
					continueFalse++;
				} else {
					continueFalse = 0;
					addRoomRecords(recordPage);
				}
			} catch (RequestException e) {
				continueFalse++;
				logger.error("room[{}-{}] record page get error", building.getName(), roomName, e);
				scanerReturn.addUnSuccessRoom(new Room(building.getName(), floor, room));
			} catch (PageParseException e) {
				continueFalse++;
				scanerReturn.addUnSuccessRoom(new Room(building.getName(), floor, room));
				logger.error("room[{}-{}] record page get error: page can not paser", building.getName(), roomName, e);
			}
			if(continueFalse >  3) {
				logger.info("floor[{}-{}] mx room is {}", building.getName(), floor, (room - continueFalse));
				break;
			}
		}
		return scanerReturn;
	}
	
	private void perpare() throws RequestException {
		logger.debug("floor[{}-{}] record scaner http client perpare start", building.getName(), floor);
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("floor[{}-{}] record scaner http client perpare finish", building.getName(), floor);
	}
	
	private RecordPage getRecordPage(int floor, int roomNO) throws RequestException, PageParseException {
		String roomName = Room.getRoomName(floor, roomNO);
		logger.debug("room[{}-{}] record page get start", building.getName(), roomName);
		RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
		httpClient.executeRequest(recordRequest);
		RecordPage recordPage =  new RecordPage();
		recordPage.parse(httpClient.getCurrentDocument());
		if(!recordPage.isErrorPage()) {
			logger.debug("room[{}-{}] record page get success: remain are[{}], charge are[{}]", building.getName(), roomName, recordPage.getRemainLines(), recordPage.getChargeLines());
		} 
		return recordPage;
	}
	
	private void addRoomRecords(RecordPage recordPage) {
		for(RecordRemainLine remainLine : recordPage.getRemainLines()) {
			RemainRecord remainRecord = new RemainRecord(recordPage.getBuildingName(), recordPage.getRoomName(), remainLine.getDate(), remainLine.getRemain());
			scanerReturn.addRemainRecord(remainRecord);
		}
		for(RecordChargeLine chargeLine : recordPage.getChargeLines()) {
			ChargeRecord chargeRecord = new ChargeRecord(recordPage.getBuildingName(), recordPage.getRoomName(), chargeLine.getDate(), chargeLine.getPower(), chargeLine.getMoney());
			scanerReturn.addChargeRecord(chargeRecord);
		}
	}

}

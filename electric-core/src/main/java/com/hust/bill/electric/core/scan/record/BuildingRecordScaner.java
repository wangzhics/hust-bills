package com.hust.bill.electric.core.scan.record;

import java.util.ArrayList;
import java.util.List;
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
import com.hust.bill.electric.service.IRecordService;


public class BuildingRecordScaner implements Callable<BuildingRecordScanerReturn>  {

	private static Logger logger = LoggerFactory.getLogger(BuildingRecordScaner.class);
	
	private Building building;
	private IRecordService recordService;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private List<Room> unSuccessRoomList = new ArrayList<Room>(100);
	private List<RemainRecord> remainList = new ArrayList<RemainRecord>(2000);
	private List<ChargeRecord> chargeList = new ArrayList<ChargeRecord>(200);
	
	public BuildingRecordScaner(Building building, IRecordService recordService) {
		this.building = building;
		this.recordService = recordService;
	}
	
	
	@Override
	public BuildingRecordScanerReturn call() throws Exception {
		
		perpare();
		
		for(int i = 1; i <= building.getFloor(); i ++) {
			tryOneFloor(i);
		}
		
		logger.info("buildings[{}] remain count is [{}], charge cout is[{}], unSuccessRoom count is{[]}", building.getName(), remainList.size(), unSuccessRoomList.size());
		
		saveRecords();
		
		Room[] unSuccessRooms = unSuccessRoomList.toArray(new Room[0]);
		return new BuildingRecordScanerReturn(building.getName(), remainList.size(), remainList.size(), unSuccessRooms);
	}
	
	private void perpare() throws RequestException {
		logger.debug("try perpare get records of [{}]", building.getName());
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("perpare get records of [{}] Finish", building.getName());
	}
	
	private void tryOneFloor(int floor) throws RequestException, PageParseException {
		int continueFalse = 0;
		for(int room = 1; room < 100; room ++) {
			try {
				RecordPage recordPage = getRecordPage(floor, room);
				if(recordPage.isErrorPage()) {
					continueFalse++;
				} else {
					addRoomRecords(recordPage);
				}
			} catch (RequestException e) {
				unSuccessRoomList.add(new Room(building.getName(), floor, room));
			}
			if(continueFalse >  3) {
				logger.info("try over 3 times, building[{}] max room no of floor[{}] is {}", building.getName(), floor, (room - continueFalse));
				break;
			}
		}
	}
	
	private RecordPage getRecordPage(int floor, int roomNO) throws RequestException, PageParseException {
		String roomName = Room.getRoomName(floor, roomNO);
		logger.debug("try get records of [{}-{}]", building.getName(), roomName);
		RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
		httpClient.executeRequest(recordRequest);
		RecordPage recordPage =  new RecordPage();
		recordPage.parse(httpClient.getCurrentDocument());
		if(!recordPage.isErrorPage()) {
			logger.debug("try get records of [{}-{}] success: remain are[{}], charge are[{}]", building.getName(), roomName, recordPage.getRemainLines(), recordPage.getChargeLines());
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

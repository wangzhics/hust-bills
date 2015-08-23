package com.hust.bill.electric.core.scan.room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.PageParseException;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.scan.record.BuildingRecordScaner;
import com.hust.bill.electric.service.IRecordService;


public class BuildingRoomScaner implements Callable<BuildingRoomScanResult> {

private static Logger logger = LoggerFactory.getLogger(BuildingRecordScaner.class);
	
	private Building building;
	private List<Room> roomList = new ArrayList<Room>(100);
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	public BuildingRoomScaner(Building building) {
		this.building = building;
	}


	@Override
	public BuildingRoomScanResult call() throws Exception {
		
		perpare();
		
		int continueFalse = 0;
		for(int floor = 1; floor <= building.getFloor(); floor++) {
			for(int roomNO = 1; roomNO < 100; roomNO ++) {
				RecordPage recordPage = getRecordPage(floor, roomNO);
				if(recordPage.hasRecord()) {
					roomList.add(new Room(building.getName(), floor, roomNO));
				} else {
					continueFalse ++;
				} 
				if(continueFalse >  3) {
					logger.info("floor[{}-{}] max room no is {}", building.getName(), floor, (roomNO - continueFalse));
					break;
				}
			}
		}
		
		return new BuildingRoomScanResult(roomList.toArray(new Room[0]));
		
	}
	
	
	private void perpare() throws RequestException {
		logger.debug("building[{}] room scaner http client perpare start", building.getName());
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("building[{}] room scaner http client perpare finish", building.getName());
	}
	
	
	private RecordPage getRecordPage(int floor, int roomNO) throws RequestException, PageParseException {
		String roomName = Room.getRoomName(floor, roomNO);
		logger.debug("room[{}-{}] record page get start", building.getName(), roomName);
		RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
		httpClient.executeRequest(recordRequest);
		RecordPage recordPage =  new RecordPage();
		recordPage.parse(httpClient.getCurrentDocument());
		if(recordPage.hasRecord()) {
			logger.debug("room[{}-{}] record page get success", building.getName(), roomName, recordPage.getRemainLines(), recordPage.getChargeLines());
		} 
		return recordPage;
	}

}

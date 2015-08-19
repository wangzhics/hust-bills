package com.hust.bill.electric.core.task.room;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.task.record.RecordScanRunnable;
import com.hust.bill.electric.core.thread.SubThreadRunnable;

public class RoomScanRunnable extends SubThreadRunnable {

	private static Logger logger = LoggerFactory.getLogger(RecordScanRunnable.class);
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private Building building;
	
	private List<Room> roomList = new ArrayList<Room>(100);
	
	public RoomScanRunnable(Building building) {
		super("[" + building.getName() + "] Room Scan Thread");
		this.building = building;
	}

	@Override
	public void execute() {
		try {
			perpare();
			logger.info("Try scan rooms of [" + building.getName() + "]");
			for(int i = 1; i <= building.getFloor(); i ++) {
				tryOneFloor(i);
			}
			logger.info("Scan rooms of [" + building.getName() + "] Finish");
		} catch (RequestException e) {
			e.printStackTrace();
		} 
	}
	
	private void perpare() throws RequestException {
		logger.info("Try perpare scan rooms of [" + building.getName() + "]");
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.info("Perpare scan rooms of [" + building.getName() + "] Finish");
		
	}
	
	private void tryOneFloor(int floor) throws RequestException {
		int continueFalse = 0;
		RecordPage recordPage = null;
		for(int roomNo = 1; roomNo < 100; roomNo ++) {
			String roomName = RemainRecord.getRoomName(floor, roomNo);
			logger.info("Validate [" + building.getName() + "-" + roomName + "]");
			RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNo);
			httpClient.executeRequest(recordRequest);
			recordPage = new RecordPage(httpClient.getCurrentDocument());
			if(recordPage.isErrorPage()) {
				continueFalse ++;
			} else {
				continueFalse = 0;
				logger.info("[" + building.getName() + "-" + roomName + "] Exist");
				Room room = new Room(building.getName(), roomName, floor);
				roomList.add(room);
			}
			if(continueFalse >  3) {
				logger.info("try over 3 times, [" + building.getName() + "-" + floor + "] max room is " + (roomNo - continueFalse));
				break;
			}
		}
	}

}

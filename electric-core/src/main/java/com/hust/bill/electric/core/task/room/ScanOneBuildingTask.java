package com.hust.bill.electric.core.task.room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.IRoomService;

public class ScanOneBuildingTask extends Task {

	private final static Logger logger = LoggerFactory.getLogger(ScanOneBuildingTask.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Building building;
	private IRoomService roomService;
	private List<Room> roomList = new ArrayList<Room>(100);
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private boolean autoOperate = false;
	
	public ScanOneBuildingTask(Building building, IRoomService roomService) {
		super(building.getName() + "[" + sdf.format(new Date()) + "]");
		this.building = building;
		this.roomService = roomService;
	}

	@Override
	public void run() {
		RoomTaskBean taskBean = new RoomTaskBean();
		taskBean.setName(getName());
		taskBean.setBuildingName(building.getName());
		taskBean.setStartTime(new Date());
		taskBean.setStatus(TaskStatus.PERPARE);
		
		try {
			roomService.addTask(taskBean);
			finishPerpare(building.getFloor() + 3);
			
			perpare();
			
			int continueFalse = 0;
			for(int floor = 1; floor <= building.getFloor(); floor++) {
				for(int roomNO = 1; roomNO < 100; roomNO ++) {
					String roomName = Room.getRoomName(floor, roomNO);
					logger.debug("room[{}-{}] record page get start", building.getName(), roomName);
					RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
					httpClient.executeRequest(recordRequest);
					if(RecordPage.hasRecord(httpClient.getCurrentDocument())) {
						roomList.add(new Room(building.getName(), floor, roomNO));
					} else {
						continueFalse ++;
					} 
					if(continueFalse >  3) {
						logger.info("floor[{}-{}] max room no is {}", building.getName(), floor, (roomNO - continueFalse));
						break;
					}
				}
				stepIn();
			}
		} catch (Exception e) {
			
		}
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

}

package com.hust.bill.electric.core.task.room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.IRoomService;

public class RoomScanTask extends Task {

	private final static Logger logger = LoggerFactory.getLogger(RoomScanTask.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	protected Building building;
	protected List<Room> roomList = new ArrayList<Room>(100);
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	
	public RoomScanTask(Building building, IRoomService roomService) {
		super(roomService);
		this.building = building;
	}
	
	@Override
	protected TaskBean createTaskBean() {
		RoomTaskBean taskBean = new RoomTaskBean();
		taskBean.setBuildingName(building.getName());
		taskBean.setName(building.getName() + " - " + sdf.format(new Date()));
		return taskBean;
	}

	@Override
	protected int perpare() throws Exception {
		logger.debug("task[{}]: perpare httpClient", getName());
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("task[{}]: perpare httpClient finish", getName());
		return building.getFloor();
	}

	@Override
	protected void execute() throws Exception {
		logger.debug("task[{}] start execute", getName());
		for(int floor = 1; floor <= building.getFloor(); floor++) {
			int continueFalse = 0;
			for(int roomNO = 1; roomNO < 100; roomNO ++) {
				String roomName = Room.getRoomName(floor, roomNO);
				logger.debug("task[{}]: get room{} record page", getName(), roomName);
				RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
				httpClient.executeRequest(recordRequest);
				if(RecordPage.hasRecord(httpClient.getCurrentDocument())) {
					logger.debug("task[{}]: get room{} record page success", getName(), roomName);
					roomList.add(new Room(building.getName(), floor, roomNO));
				} else {
					logger.debug("task[{}]: get room{} record page failed", getName(), roomName);
					continueFalse ++;
				} 
				if(continueFalse >  3) {
					logger.info("task[{}]: floor[{}] max room no is {}",getName(), floor, (roomNO - continueFalse));
					break;
				}
			}
			stepIn();
		}
		logger.info("room scan task[{}] execute finish, room count is {}", getName(), roomList.size());
		
	}

	@Override
	protected void saveToDataBase() {
		logger.debug("task[{}]: save taskResult[{}] to database", getName(), roomList.size());
		RoomTaskResultBean[] taskResults = new RoomTaskResultBean[roomList.size()];
		int i = 0;
		for(Room r : roomList) {
			taskResults[i] = new RoomTaskResultBean();
			taskResults[i].setTaskID(taskBean.getId());
			taskResults[i].setBuildingName(getTaskBean().getBuildingName());
			taskResults[i].setRoomName(r.getRoomName());
			taskResults[i].setRoomFloor(r.getRoomFloor());
			taskResults[i].setRoomNO(r.getRoomNO());
			i++;
		}
		getTaskService().addTaskResults(taskResults);
		logger.debug("task[{}]: save taskResult[{}] to database success", getName(), taskResults.length);
	}
	
	protected IRoomService getTaskService() {
		return (IRoomService)taskService;
	}
	
	protected RoomTaskBean getTaskBean() {
		return (RoomTaskBean)taskBean;
	}

}

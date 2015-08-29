package com.hust.bill.electric.core.task.room;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.Operation;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.room.RoomOperateBean;
import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.IRoomService;

public class RoomScanTask extends Task {

	
	public final static String INITIAL_TASK_NAME = "Initial-Room";
	private final static Logger logger = LoggerFactory.getLogger(RoomScanTask.class);
	
	private Building building;
	private IRoomService roomService;
	private RoomTaskBean taskBean;
	private List<Room> roomList = new ArrayList<Room>(100);
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private boolean autoOperate = false;
	
	public RoomScanTask(Building building, IRoomService roomService) {
		this(building, roomService, false);
	}
	
	public RoomScanTask(Building building, IRoomService roomService, boolean isInitial) {
		this.building = building;
		this.roomService = roomService;
		taskBean = new RoomTaskBean();
		taskBean.setBuildingName(building.getName());
		taskBean.setStatus(TaskStatus.PERPARE);
		if(isInitial) {
			this.autoOperate = true;
			taskBean.setName(INITIAL_TASK_NAME + "-" + building.getName());
		} else {
			taskBean.setName("r[" + building.getName() + "]");
		}
		roomService.addTask(taskBean);
	}

	@Override
	public void run() {
		try {
			perpare();
			
			finishPerpare(building.getFloor() + 2);
			
			scan();
			
			saveToDataBase();
			stepIn();
			
			finishTask(TaskStatus.FINISH);
			stepIn();
			
		} catch (Exception e) {
			logger.error("room scan task[{}] failed", getName() ,e);
			finishTask(TaskStatus.ERROR);
		}
	}
	
	private void scan() throws RequestException{
		logger.debug("room scan task[{}] execute start", getName());
		for(int floor = 1; floor <= building.getFloor(); floor++) {
			int continueFalse = 0;
			for(int roomNO = 1; roomNO < 100; roomNO ++) {
				String roomName = Room.getRoomName(floor, roomNO);
				logger.debug("room scan task[{}]: get room{} record page", getName(), roomName);
				RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
				httpClient.executeRequest(recordRequest);
				if(RecordPage.hasRecord(httpClient.getCurrentDocument())) {
					logger.debug("room scan task[{}]: get room{} record page success", getName(), roomName);
					roomList.add(new Room(building.getName(), floor, roomNO));
				} else {
					logger.debug("room scan task[{}]: get room{} record page failed", getName(), roomName);
					continueFalse ++;
				} 
				if(continueFalse >  3) {
					logger.info("room scan task[{}]: floor[{}] max room no is {}",getName(), floor, (roomNO - continueFalse));
					break;
				}
			}
			stepIn();
		}
		logger.info("room scan task[{}] execute finish, room count is {}", getName(), roomList.size());
	}
	
	private void perpare() throws RequestException {
		logger.debug("perpare room scan task[{}]: perpare httpClient", getName());
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("perpare room scan task[{}]: perpare httpClient", getName());
	}
	
	private void saveToDataBase() {
		logger.debug("room scan task[{}]: save taskResult[{}] to database", getName(), roomList.size());
		RoomTaskResultBean[] taskResults = new RoomTaskResultBean[roomList.size()];
		int i = 0;
		for(Room r : roomList) {
			taskResults[i] = new RoomTaskResultBean();
			taskResults[i].setTaskID(taskBean.getId());
			taskResults[i].setBuildingName(taskBean.getBuildingName());
			taskResults[i].setRoomName(r.getRoomName());
			taskResults[i].setRoomFloor(r.getRoomFloor());
			taskResults[i].setRoomNO(r.getRoomNO());
			i++;
		}
		roomService.addTaskResults(taskResults);
		logger.debug("room scan task[{}]: save taskResult[{}] to database finish", getName(), taskResults.length);
		
		if(autoOperate) {
			logger.debug("room scan task[{}]: save autoOperate[{}] to database", getName(), roomList.size());
			RoomTaskResultBean[] taskResultBeans = roomService.getTaskResultsByTaskID(taskBean.getId());
			RoomOperateBean[] operareBeans = new RoomOperateBean[taskResultBeans.length];
			i = 0;
			for(RoomTaskResultBean resultBean : taskResultBeans) {
				operareBeans[i] = RoomOperateBean.newOperateBean(taskBean, resultBean, Operation.ADD);
				i++;
			}
			roomService.operate(operareBeans);
			logger.debug("building scan task[{}]: save autoOperate[{}] to database finish", getName(), operareBeans.length);
		}
		logger.debug("building scan: save to database finish");
	}
	
	private void finishTask(TaskStatus taskStatus) {
		logger.debug("room scan task[{}] finish[{}]", getName(), taskStatus.getDescription());
		taskBean.setStatus(taskStatus);
		roomService.finishTask(taskBean.getId(), taskStatus);
	}
	

	@Override
	public String getName() {
		return taskBean.getName();
	}

}

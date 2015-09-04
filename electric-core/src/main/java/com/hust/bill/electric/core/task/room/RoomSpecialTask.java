package com.hust.bill.electric.core.task.room;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.Operation;
import com.hust.bill.electric.bean.task.room.RoomOperateBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.service.IRoomService;

public class RoomSpecialTask extends RoomScanTask {
	
	private final static Logger logger = LoggerFactory.getLogger(RoomSpecialTask.class);
	
	private int[] floors;
	
	public RoomSpecialTask(Building building, int[] floors, IRoomService roomService) {
		super(building, roomService);
		this.floors = floors;
	}

	@Override
	protected void execute() throws Exception {
		logger.debug("task[{}] start execute", getName());
		for(int floor : floors) {
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
		super.saveToDataBase();
		logger.debug("task[{}]: save operate[{}] to database", getName(), roomList.size());
		RoomTaskResultBean[] taskResultBeans = getTaskService().getTaskResultsByTaskID(taskBean.getId());
		RoomOperateBean[] operareBeans = new RoomOperateBean[taskResultBeans.length];
		int i = 0;
		for(RoomTaskResultBean resultBean : taskResultBeans) {
			operareBeans[i] = RoomOperateBean.newOperateBean(getTaskBean(), resultBean, Operation.ADD);
			i++;
		}
		getTaskService().operate(operareBeans);
		logger.debug("task[{}]: save operate[{}] to database success", getName(), operareBeans.length);
	}

}

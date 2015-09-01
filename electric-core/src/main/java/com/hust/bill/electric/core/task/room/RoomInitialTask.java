package com.hust.bill.electric.core.task.room;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.Operation;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.room.RoomOperateBean;
import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;
import com.hust.bill.electric.service.IRoomService;

public class RoomInitialTask extends RoomScanTask {
	
	public final static String INITIAL_TASK_NAME = "Initial-Room";
	private final static Logger logger = LoggerFactory.getLogger(RoomInitialTask.class);

	public RoomInitialTask(Building building, IRoomService roomService) {
		super(building, roomService);
	}
	
	@Override
	protected TaskBean createTaskBean() {
		RoomTaskBean taskBean = new RoomTaskBean();
		taskBean.setBuildingName(building.getName());
		taskBean.setName(INITIAL_TASK_NAME + " - " + building.getName());
		return taskBean;
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

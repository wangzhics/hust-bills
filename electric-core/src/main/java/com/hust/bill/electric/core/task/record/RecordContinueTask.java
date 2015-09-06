package com.hust.bill.electric.core.task.record;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

public class RecordContinueTask extends RecordScanTask {

	private BigInteger taskId;
	private final static Logger logger = LoggerFactory.getLogger(RecordContinueTask.class);
	
	public RecordContinueTask(BigInteger taskId, IBuildingService buildingService, IRoomService roomService,
			IRecordService recordService) {
		super(buildingService, roomService, recordService);
		this.taskId = taskId;
	}
	
	@Override
	public void create() throws Exception {
		this.taskBean = createTaskBean();
	}
	

	@Override
	protected TaskBean createTaskBean() {
		return getTaskService().getTaskById(taskId);
	}
	
	@Override
	protected int perpare() throws Exception {
		logger.debug("task[{}]: try get unSuccess buildings", getName());
		buildings =getTaskService().getUnSuccessBuildings(taskId);
		logger.debug("task[{}]: try get unSuccess buildings success, count is {}", getName(), buildings.length);
		return buildings.length;
	}
	
}

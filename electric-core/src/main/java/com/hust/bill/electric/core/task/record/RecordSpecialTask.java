package com.hust.bill.electric.core.task.record;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.record.RecordTaskBean;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

public class RecordSpecialTask extends RecordScanTask{

	private final static Logger logger = LoggerFactory.getLogger(RecordSpecialTask.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public RecordSpecialTask(IBuildingService buildingService, IRoomService roomService, IRecordService recordService) {
		super(buildingService, roomService, recordService);
	}
	
	public void create(Building[] buildings) throws Exception {
		super.create();
		this.buildings = buildings;
	}
	
	@Override
	protected TaskBean createTaskBean() {
		RecordTaskBean taskBean = new RecordTaskBean();
		taskBean.setName("sp - record scan - " + sdf.format(new Date()));
		return taskBean;
	}
	
	@Override
	protected int perpare() throws Exception {
		logger.debug("task[{}]:  building count is {}", getName(), buildings.length);
		return buildings.length;
	}
	
	

}

package com.hust.bill.electric.core.task.record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.record.RecordTaskBean;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;
import com.hust.bill.electric.service.ITaskService;

public class RecordScanTask extends Task {

	private final static Logger logger = LoggerFactory.getLogger(RecordScanTask.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private IBuildingService buildingService;
	private IRoomService roomService;
	private IRecordService recordService;
	
	private Building[] buildings;
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private List<Future<ScanByBuildingResult>> resultList = new ArrayList<Future<ScanByBuildingResult>>(100);
	
	
	public RecordScanTask(ITaskService taskService, IBuildingService buildingService, IRoomService roomService,
			IRecordService recordService) {
		super(taskService);
		this.buildingService = buildingService;
		this.roomService = roomService;
		this.recordService = recordService;
	}

	@Override
	protected TaskBean createTaskBean() {
		RecordTaskBean taskBean = new RecordTaskBean();
		taskBean.setName("record scan - " + sdf.format(new Date()));
		return taskBean;
	}

	@Override
	protected int perpare() throws Exception {
		logger.debug("task[{}]: try get buildings", getName());
		buildings = buildingService.getAll();
		logger.debug("task[{}]: try get buildings success, count is {}", getName(), buildings.length);
		return buildings.length;
	}

	@Override
	protected void execute() throws Exception {
		logger.debug("task[{}]: create scaner by building", getName());
		for(Building building : buildings) {
			ScanByBuildingCallable callable = new ScanByBuildingCallable(getTaskBean(), building, roomService, recordService);
			Future<ScanByBuildingResult> result = executorService.submit(callable);
			resultList.add(result);
		}
		logger.debug("task[{}]:  create scaner by building finish", getName());
		
		logger.debug("task[{}]: sub scaner start execute", getName());
		for(Future<ScanByBuildingResult> result : resultList) {
			result.get();
			stepIn();
		}
	}

	@Override
	protected void saveToDataBase() {
		
	}
	
	protected RecordTaskBean getTaskBean() {
		return (RecordTaskBean)taskBean;
	}

}

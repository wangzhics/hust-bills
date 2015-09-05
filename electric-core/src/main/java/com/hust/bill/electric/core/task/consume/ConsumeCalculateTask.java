package com.hust.bill.electric.core.task.consume;

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
import com.hust.bill.electric.bean.task.consume.ConsumeTaskBean;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.core.task.record.RecordScanTask;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IConsumeService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

public class ConsumeCalculateTask extends Task {

	private final static Logger logger = LoggerFactory.getLogger(RecordScanTask.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private IBuildingService buildingService;
	private IRoomService roomService;
	private IRecordService recordService;
	
	private Building[] buildings;
	private ExecutorService executorService = Executors.newFixedThreadPool(1);
	private List<Future<CalculateByBuildingResult>> resultList = new ArrayList<Future<CalculateByBuildingResult>>(100);
	
	public ConsumeCalculateTask(IConsumeService consumeService, IBuildingService buildingService, IRoomService roomService, IRecordService recordService) {
		super(consumeService);
		this.buildingService = buildingService;
		this.roomService = roomService;
		this.recordService = recordService;
	}

	@Override
	protected TaskBean createTaskBean() {
		ConsumeTaskBean taskBean = new ConsumeTaskBean();
		taskBean.setName("consume calculate - " + sdf.format(new Date()));
		return taskBean;
	}

	@Override
	protected int perpare() throws Exception {
		logger.debug("consume[{}]: try get buildings", getName());
		buildings = buildingService.getAll();
		logger.debug("consume[{}]: try get buildings success, count is {}", getName(), buildings.length);
		return buildings.length;
	}

	@Override
	protected void execute() throws Exception {
		logger.debug("consume[{}]: create scaner by building", getName());
		for(Building building : buildings) {
			CalculateByBuildingCallable callable = new CalculateByBuildingCallable(getTaskBean(), building, roomService, recordService, getTaskService());
			Future<CalculateByBuildingResult> result = executorService.submit(callable);
			resultList.add(result);
		}
		logger.debug("consume[{}]:  create scaner by building finish", getName());
		int consumeCount = 0;
		logger.debug("task[{}]: sub scaner start execute", getName());
		for(Future<CalculateByBuildingResult> result : resultList) {
			try {
				CalculateByBuildingResult calculateResult = result.get();
				consumeCount = consumeCount + calculateResult.getResultBean().getConsumeCount();
				stepIn();
			} catch (Throwable e) {
				executorService.shutdownNow();
				throw e;
			}
		}
		logger.info("consume[{}]: finish consume count {}, charge count {}", getName(), consumeCount);
	}

	@Override
	protected void saveToDataBase() {
		
	}

	protected ConsumeTaskBean getTaskBean() {
		return (ConsumeTaskBean)taskBean;
	}
	
	protected IConsumeService getTaskService() {
		return (IConsumeService)taskService;
	}
	
}

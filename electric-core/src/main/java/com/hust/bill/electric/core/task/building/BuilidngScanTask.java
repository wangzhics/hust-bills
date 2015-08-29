package com.hust.bill.electric.core.task.building;

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
import com.hust.bill.electric.bean.task.Operation;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.building.BuildingOperateBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.IBuildingService;

public class BuilidngScanTask extends Task {

	public final static String INITIAL_TASK_NAME = "Initial-Building";
	private final static Logger logger = LoggerFactory.getLogger(BuilidngScanTask.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private boolean autoOperate = false;
	ElectricHttpClient httpClient = new ElectricHttpClient();
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private List<Future<ScanByAreaResult>> resultList = new ArrayList<Future<ScanByAreaResult>>(10);
	private List<Building> buildingList = new ArrayList<Building>(100);
	
	private IBuildingService buildingService;
	private BuildingTaskBean taskBean;
	private String[] areas;
	
	public BuilidngScanTask(IBuildingService buildingService) {
		this(buildingService, false);
	}
	
	public BuilidngScanTask(IBuildingService buildingService, boolean isInitial) {
		this.buildingService = buildingService;
		taskBean = new BuildingTaskBean();
		if(isInitial) {
			this.autoOperate = true;
			taskBean.setName(INITIAL_TASK_NAME);
		} else {
			taskBean.setName("b[" + sdf.format(new Date()) + "]");
		}
		taskBean.setStatus(TaskStatus.PERPARE);
		buildingService.addTask(taskBean);
	}
	
	@Override
	public String getName() {
		return taskBean.getName();
	}
	
	@Override
	public void run() {
		try {
			taskBean.setStatus(TaskStatus.RUNNING);
			buildingService.updateTaskStatus(taskBean.getId(), TaskStatus.RUNNING);
			
			perpareAreas();
			finishPerpare(areas.length + 3);
			
			createCallable();
			stepIn();
			
			executeCallable();
			
			saveToDataBase();
			stepIn();
			
			finishTask(TaskStatus.FINISH);
			stepIn();
			
		} catch (Exception e) {
			logger.error("building scan task[{}] failed", getName() ,e);
			finishTask(TaskStatus.ERROR);
		}  
	}
	
	private void perpareAreas() throws Exception {
		logger.debug("perpare building scan task[{}]: get areas", getName());
		httpClient.perpare();
		AreaPage areaPage = new AreaPage();
		areaPage.parse(httpClient.getCurrentDocument());
		this.areas = areaPage.getAreas();
		logger.debug("perpare building scan task[{}]: get areas success {}", getName() , areas.toString());
	}
	
	private void createCallable() {
		logger.debug("building scan task[{}]: create area building scaner", getName());
		for(String area : areas) {
			ScanByAreaCallable buildingScaner = new ScanByAreaCallable(area);
			Future<ScanByAreaResult> result = executorService.submit(buildingScaner);
			resultList.add(result);
		}
		logger.debug("building scan task[{}]: create area building scaner finish", getName());
	}
	
	private void executeCallable() throws Exception {
		logger.debug("building scan task[{}]: area building scaner execute", getName());
		for(Future<ScanByAreaResult> result : resultList) {
			ScanByAreaResult scanByAreaResult = result.get();
			buildingList.addAll(scanByAreaResult.getBuildingList());
			stepIn();
		}
		logger.debug("building scan task[{}]: area building scaner execute finish", getName());
	}
	
	private void saveToDataBase() {
		logger.debug("building scan task[{}]: save taskResult[{}] to database", getName(), buildingList.size());
		BuildingTaskResultBean[] taskResults = new BuildingTaskResultBean[buildingList.size()];
		int i = 0;
		for(Building b : buildingList) {
			taskResults[i] = new BuildingTaskResultBean();
			taskResults[i].setTaskID(taskBean.getId());
			taskResults[i].setAreaName(b.getArea());
			taskResults[i].setBuildingName(b.getName());
			taskResults[i].setBuildingFloor(b.getFloor());
			i++;
		}
		buildingService.addTaskResults(taskResults);
		logger.debug("building scan task[{}]: save taskResult[{}] to database finish", getName(), taskResults.length);
		
		if(autoOperate) {
			logger.debug("building scan task[{}]: save autoOperate[{}] to database", getName(), buildingList.size());
			BuildingTaskResultBean[] taskResultBeans = buildingService.getTaskResultsByTaskID(taskBean.getId());
			BuildingOperateBean[] operareBeans = new BuildingOperateBean[taskResultBeans.length];
			i = 0;
			for(BuildingTaskResultBean resultBean : taskResultBeans) {
				operareBeans[i] = BuildingOperateBean.newOperateBean(taskBean, resultBean, Operation.ADD);
				i++;
			}
			buildingService.operate(operareBeans);
			logger.debug("building scan task[{}]: save autoOperate[{}] to database finish", getName(), operareBeans.length);
		}
		logger.debug("building scan: save to database finish");
	}
	
	private void finishTask(TaskStatus taskStatus) {
		logger.debug("building scan task[{}] finish[{}]", getName(), taskStatus.getDescription());
		taskBean.setStatus(taskStatus);
		buildingService.finishTask(taskBean.getId(), taskStatus);
	}
	
	public void setAutoOperate(boolean autoOperate) {
		this.autoOperate = autoOperate;
	}
	
}

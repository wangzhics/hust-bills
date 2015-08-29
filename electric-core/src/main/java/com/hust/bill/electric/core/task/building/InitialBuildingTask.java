package com.hust.bill.electric.core.task.building;

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

public class InitialBuildingTask extends Task {

	public final static String TASK_NAME = "Initial-Building";
	private final static Logger logger = LoggerFactory.getLogger(ScanAllTask.class);
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private List<Future<ScanByAreaResult>> resultList = new ArrayList<Future<ScanByAreaResult>>(10);
	private List<Building> buildingList = new ArrayList<Building>(100);
		
	private IBuildingService buildingService;
	ElectricHttpClient httpClient = new ElectricHttpClient();
	
	public InitialBuildingTask(IBuildingService buildingService) {
		super(TASK_NAME);
		this.buildingService = buildingService;
	}

	@Override
	public void run() {
		BuildingTaskBean taskBean = new BuildingTaskBean();
		taskBean.setName(getName());
		taskBean.setStartTime(new Date());
		taskBean.setStatus(TaskStatus.PERPARE);
		
		try {
			buildingService.addTask(taskBean);
			
			logger.debug("building scan: start task");
			taskBean.setStatus(TaskStatus.RUNNING);
			buildingService.updateTaskStatus(taskBean.getId(), TaskStatus.RUNNING);
			
			logger.debug("perpare building scan: get areas");
			httpClient.perpare();
			AreaPage areaPage = new AreaPage();
			areaPage.parse(httpClient.getCurrentDocument());
			logger.debug("perpare building scan: get areas success{}", areaPage.getAreas().toString());
			
			finishPerpare(areaPage.getAreas().length + 3);
			
			logger.debug("perpare building scan: start scan by area");
			for(String area : areaPage.getAreas()) {
				ScanByAreaCallable buildingScaner = new ScanByAreaCallable(area);
				Future<ScanByAreaResult> result = executorService.submit(buildingScaner);
				resultList.add(result);
			}
			logger.debug("perpare building scan: start scan by area finish");
			stepIn();
			
			logger.debug("building scan: get scan result");
			for(Future<ScanByAreaResult> result : resultList) {
				ScanByAreaResult scanByAreaResult = result.get();
				buildingList.addAll(scanByAreaResult.getBuildingList());
				stepIn();
			}
			logger.debug("building scan: get scan result finish");
			
			logger.debug("building scan: save to database");
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
			BuildingTaskResultBean[] taskResultBeans = buildingService.getTaskResultsByTaskID(taskBean.getId());
			BuildingOperateBean[] operareBeans = new BuildingOperateBean[buildingList.size()];
			i = 0;
			for(BuildingTaskResultBean resultBean : taskResultBeans) {
				operareBeans[i] = BuildingOperateBean.newOperateBean(taskBean, resultBean, Operation.ADD);
				i++;
			}
			buildingService.operate(operareBeans);
			logger.debug("building scan: save to database finish");
			stepIn();
			
			logger.debug("building scan: finish task");
			buildingService.finishTask(taskBean.getId(), TaskStatus.FINISH);
			taskBean.setStatus(TaskStatus.FINISH);
			stepIn();
			
		} catch (Exception e) {
			logger.error("building scan failed", e);
			taskBean.setStatus(TaskStatus.ERROR);
			buildingService.finishTask(taskBean.getId(), TaskStatus.ERROR);
		}  
	}
	
}

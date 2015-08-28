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
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.IBuildingService;

public class ScanAllTask extends Task{

	private static Logger logger = LoggerFactory.getLogger(ScanAllTask.class);
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private List<Future<ScanByAreaResult>> resultList = new ArrayList<Future<ScanByAreaResult>>(10);
	private List<Building> buildingList = new ArrayList<Building>(100);
		
	private BuildingTaskBean buildingTask;
	private IBuildingService buildingService;
	ElectricHttpClient httpClient = new ElectricHttpClient();
	
	public ScanAllTask(BuildingTaskBean buildingTask, IBuildingService buildingService) {
		super(buildingTask.getId());
		this.buildingTask = buildingTask;
		this.buildingService = buildingService;
	}

	@Override
	public void run() {
		buildingTask.setStatus(TaskStatus.RUNNING);
		buildingService.updateTaskSatus(buildingTask);
		try {
			logger.debug("perpare building scan: get areas");
			httpClient.perpare();
			AreaPage areaPage = new AreaPage();
			areaPage.parse(httpClient.getCurrentDocument());
			logger.debug("perpare building scan: get areas success{}", areaPage.getAreas().toString());
			
			finishPerpare(areaPage.getAreas().length);
			
			logger.debug("perpare area building scaner");
			for(String area : areaPage.getAreas()) {
				ScanByAreaCallable buildingScaner = new ScanByAreaCallable(area);
				Future<ScanByAreaResult> result = executorService.submit(buildingScaner);
				resultList.add(result);
			}
			logger.debug("perpare area building scaner finish");
			
			for(Future<ScanByAreaResult> result : resultList) {
				ScanByAreaResult scanByAreaResult = result.get();
				buildingList.addAll(scanByAreaResult.getBuildingList());
				stepIn();
			}
			BuildingTaskResultBean[] buildingTaskResults = new BuildingTaskResultBean[buildingList.size()];
			int i = 0;
			for(Building b : buildingList) {
				buildingTaskResults[i] = new BuildingTaskResultBean();
				buildingTaskResults[i].setTaskID(buildingTask.getId());
				buildingTaskResults[i].setAreaName(b.getArea());
				buildingTaskResults[i].setBuildingName(b.getName());
				buildingTaskResults[i].setBuildingFloor(b.getFloor());
				i++;
			}
			buildingTask.setEndTime(new Date());
			buildingTask.setResultCount(buildingList.size());
			buildingTask.setStatus(TaskStatus.FINISH);
			buildingService.finishTask(buildingTask, buildingTaskResults);
		} catch (Exception e) {
			logger.error("building scan failed", e);
			finishTaskWithError();
		}  
	}
	
	private void finishTaskWithError() {
		buildingTask.setEndTime(new Date());
		buildingTask.setResultCount(0);
		buildingTask.setStatus(TaskStatus.ERROR);
		buildingService.finishTask(buildingTask, new BuildingTaskResultBean[0]);
	}
	

}

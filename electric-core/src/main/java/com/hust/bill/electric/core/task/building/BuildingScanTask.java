package com.hust.bill.electric.core.task.building;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.page.AreaPage;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.IBuildingService;

public class BuildingScanTask extends Task {

	private final static Logger logger = LoggerFactory.getLogger(BuildingScanTask.class);
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	ElectricHttpClient httpClient = new ElectricHttpClient();
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private List<Future<ScanByAreaResult>> resultList = new ArrayList<Future<ScanByAreaResult>>(10);
	protected List<Building> buildingList = new ArrayList<Building>(100);
	
	private String[] areas;
	
	public BuildingScanTask(IBuildingService buildingService) {
		super(buildingService);
	}
	
	@Override
	protected TaskBean createTaskBean() {
		BuildingTaskBean taskBean = new BuildingTaskBean();
		taskBean.setName("buiding scan - " + sdf.format(new Date()));
		return taskBean;
	}

	@Override
	protected int perpare() throws Exception {
		logger.debug("task[{}]: try get areas", getName());
		httpClient.perpare();
		AreaPage areaPage = new AreaPage();
		areaPage.parse(httpClient.getCurrentDocument());
		this.areas = areaPage.getAreas();
		logger.debug("task[{}]: try get areas success, count is {}", getName(), areas.length);
		return areas.length;
	}

	@Override
	protected void execute() throws Exception {
		logger.debug("task[{}]: create scaner by area", getName());
		for(String area : areas) {
			ScanByAreaCallable buildingScaner = new ScanByAreaCallable(area);
			Future<ScanByAreaResult> result = executorService.submit(buildingScaner);
			resultList.add(result);
		}
		logger.debug("task[{}]:  create scaner by area finish", getName());
		
		logger.debug("task[{}]: sub scaner start execute", getName());
		for(Future<ScanByAreaResult> result : resultList) {
			try {
				ScanByAreaResult scanByAreaResult = result.get();
				buildingList.addAll(scanByAreaResult.getBuildingList());
				stepIn();
			} catch (ExecutionException e) {
				executorService.shutdownNow();
				throw e;
			}
		}
	}

	@Override
	protected void saveToDataBase() {
		logger.debug("task[{}]: save taskResult[{}] to database", getName(), buildingList.size());
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
		getTaskService().addTaskResults(taskResults);
	}
	
	protected IBuildingService getTaskService() {
		return (IBuildingService)taskService;
	}
	
	protected BuildingTaskBean getTaskBean() {
		return (BuildingTaskBean)taskBean;
	}
	
}

package com.hust.bill.electric.service;

import java.math.BigInteger;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.building.BuildingOperateBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;

public interface IBuildingService extends ITaskService {
	
	public void addTaskResults(BuildingTaskResultBean[] results);
	
	public BuildingTaskResultBean[] getTaskResultsByTaskID(BigInteger scanID);
	
	public void operate(BuildingOperateBean[] operateBeans);
	
	public BuildingOperateBean[] getAllOperation();
	
	public Building[] getAll();
	
}

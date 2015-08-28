package com.hust.bill.electric.service;

import java.math.BigInteger;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.building.BuildingOperateBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;

public interface IBuildingService {
	
	public void addTask(BuildingTaskBean taskBean);
	
	public void updateTaskSatus(BuildingTaskBean taskBean);
	
	public void finishTask(BuildingTaskBean taskBean, BuildingTaskResultBean[] results);
	
	public BuildingTaskBean[] getAllTask();
	
	public BuildingTaskResultBean[] getTaskResultsByTaskID(BigInteger scanID);
	
	public void operate(BuildingOperateBean[] operateBeans);
	
	public BuildingOperateBean[] getAllOperation();
	
	public Building[] getAll();
	
}

package com.hust.bill.electric.dao;


import java.math.BigInteger;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.building.BuildingOperateBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;

@Repository(value="buildingDAO")
public interface IBuildingDAO {

	
	public void insertTask(BuildingTaskBean taskBean);
	
	public void updateTaskEndTime(@Param("id") BigInteger id);
	
	public void updateTaskResultCount(@Param("id") BigInteger id);
	
	public void updateTaskSatus(@Param("id") BigInteger id, @Param("status") TaskStatus taskStatus);
	
	public BigInteger getTaskIDByName(@Param("name") String name);
	
	public BuildingTaskBean[] getAllTask();
	
	public void insertTaskResults(BuildingTaskResultBean[] scanResults);
	
	public BuildingTaskResultBean[] getTaskResultsByTaskID(@Param("taskID") BigInteger taskID);
	
	public void insertOperateBeans(BuildingOperateBean[] operateBeans);
	
	public BuildingOperateBean[] getOperateBeansByTaskID(@Param("taskID")BigInteger taskID);
	
	public BuildingOperateBean[] getAllOperateBeans();
	
	public void inserts(Building[] buildings);
	
	public Building[] getAll();

	public Building getByName(@Param("name") String name);
}

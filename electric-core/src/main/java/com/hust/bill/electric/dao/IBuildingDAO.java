package com.hust.bill.electric.dao;


import java.math.BigInteger;
import java.util.Date;

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
	
	public void updateTaskSatus(@Param("id") BigInteger id, @Param("status") TaskStatus taskStatus);
	
	public void updateTaskEndTime(@Param("id") BigInteger id, @Param("endTime") Date endTime);
	
	public void updateTaskResultCount(@Param("id") BigInteger id, @Param("resultCount") int resultCount);
	
	public BigInteger getTaskIDByName(@Param("name") String name);
	
	public BuildingTaskBean[] getAllTask();
	
	public void insertTaskResults(BuildingTaskResultBean[] scanResults);
	
	public BuildingTaskResultBean[] getScanResultsByTaskID(@Param("scanID") BigInteger scanID);
	
	public void insertOperateBeans(BuildingOperateBean[] operateBeans);
	
	public BuildingOperateBean[] getOperateBeansByTaskID(@Param("scanID")BigInteger scanID);
	
	public BuildingOperateBean[] getAllOperateBeans();
	
	public void inserts(Building[] buildings);
	
	public void updates(Building[] buildings);
	
	public void deletes(Building[] buildings);
	
	public Building[] getAll();

	
}

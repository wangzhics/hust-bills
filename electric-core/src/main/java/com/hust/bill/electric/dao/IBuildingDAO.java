package com.hust.bill.electric.dao;


import java.math.BigInteger;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.BuildingOperateBean;
import com.hust.bill.electric.bean.task.BuildingScanResultBean;
import com.hust.bill.electric.bean.task.BuildingScanTaskBean;

@Repository(value="buildingDAO")
public interface IBuildingDAO {

	
	public void insertScanTask(BuildingScanTaskBean taskBean);
	
	public void updateScanTaskEndTime(BigInteger bigInteger, Date endTime);
	
	public void updateScanTaskResultCount(int id, int resultCount);
	
	public BigInteger getScanTaskByTime(Date startTime);
	
	public BuildingScanTaskBean[] getAllScanTask();
	
	public void insertScanResults(BuildingScanResultBean[] scanResults);
	
	public BuildingScanResultBean[] getScanResultsByTask(BigInteger scanID);
	
	public void insertOperateBeans(BuildingOperateBean[] operateBeans);
	
	public BuildingOperateBean[] getOperateBeansByTask(BigInteger scanID);
	
	public BuildingOperateBean[] getAllOperateBeans();
	
	public void insert(Building[] buildings);
	
	public void update(Building[] buildings);
	
	public void delete(Building[] buildings);
	
	public Building[] getAll();

	
}

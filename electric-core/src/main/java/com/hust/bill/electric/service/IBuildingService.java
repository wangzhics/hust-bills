package com.hust.bill.electric.service;

import java.math.BigInteger;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.BuildingOperateBean;
import com.hust.bill.electric.bean.task.BuildingScanResultBean;
import com.hust.bill.electric.bean.task.BuildingScanTaskBean;

public interface IBuildingService {
	
	public void addScanTask(BuildingScanTaskBean taskBean);
	
	public void finishScanTask(BuildingScanTaskBean taskBean, BuildingScanResultBean[] results);
	
	public BuildingScanTaskBean[] getAllScanTask();
	
	public BuildingScanResultBean[] getScanResultsByTaskID(BigInteger scanID);
	
	public void operate(BuildingOperateBean[] operateBeans);
	
	public Building[] getAll();
	
}

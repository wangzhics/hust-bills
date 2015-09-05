package com.hust.bill.electric.service;

import java.util.Map;

import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;

public interface IConsumeService extends ITaskService {

	public Map<String, RemainRecord> getLastRemainsByBuilding(String buildingName);
	
	public void insertRecords(ConsumeTaskResultBean taskResultBean, RemainRecord[] lastRemains, Consume[] consumes);
}

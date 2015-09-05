package com.hust.bill.electric.service;

import java.util.Date;
import java.util.Map;

import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;

public interface IConsumeService extends ITaskService {

	public Map<String, Date> getLastDatesByBuilding(String buildingName);
	
	public void insertRecords(ConsumeTaskResultBean taskResultBean, Consume[] lastConsumes, Consume[] consumes);
}

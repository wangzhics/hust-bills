package com.hust.bill.electric.service;

import java.util.Date;
import java.util.Map;


import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.query.DateAverage;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;

public interface IConsumeService extends ITaskService {

	public Map<String, RemainRecord> getLastRemainsByBuilding(String buildingName);
	
	public void insertConsumes(ConsumeTaskResultBean taskResultBean, RemainRecord[] lastRemains, Consume[] consumes);
	
	public Consume[] getConsumesByRoom(String buildingName, String roomName, Date startDate, Date endDate);
	
	public DateAverage[] getDateAvgByBuilding(String buildingName, Date startDate, Date endDate);
}

package com.hust.bill.electric.service;

import java.util.Date;
import java.util.Map;


import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.query.BuildingDateAverage;
import com.hust.bill.electric.bean.query.RoomRank;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;

public interface IConsumeService extends ITaskService {

	public Map<String, RemainRecord> getLastRemainsByBuilding(String buildingName);
	
	public void insertConsumes(ConsumeTaskResultBean taskResultBean, RemainRecord[] lastRemains, Consume[] consumes);
	
	public Consume[] getConsumesByRoom(String buildingName, String roomName, Date startDate, Date endDate);
	
	public BuildingDateAverage[] getBuildingDateAvg(String buildingName, Date startDate, Date endDate);
	
	public RoomRank getRoomRank(String buildingName, String roomName, Date startDate, Date endDate);
}

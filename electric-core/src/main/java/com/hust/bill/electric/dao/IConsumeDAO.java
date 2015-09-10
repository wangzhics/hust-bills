package com.hust.bill.electric.dao;

import java.math.BigInteger;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.query.BuildingDateAverage;
import com.hust.bill.electric.bean.query.RoomRank;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskBean;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;

@Repository(value="ConsumeDAO")
public interface IConsumeDAO {
	
	public void insertTask(ConsumeTaskBean taskBean);
	
	public void updateTaskEndTime(@Param("id") BigInteger id);
	
	public void updateTaskResultCount(@Param("id") BigInteger id);
	
	public void updateTaskSatus(@Param("id") BigInteger id, @Param("status") TaskStatus taskStatus);
	
	public BigInteger getTaskIDByName(@Param("name") String name);
	
	public void insertTaskResult(ConsumeTaskResultBean scanResult);
	
	public RemainRecord[] getLastRemainsByBuilding(@Param("buildingName") String buildingName);
	
	public void updateLastRemainsByBuilding(RemainRecord[] lastRemains);
	
	public void insertConsumes(Consume[] consumes);
	
	public Consume[] getConsumesByRoom(@Param("buildingName") String buildingName, @Param("roomName") String roomName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	public BuildingDateAverage[] getBuildingDateAvg(@Param("buildingName") String buildingName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	public RoomRank getRoomRank(@Param("buildingName") String buildingName, @Param("roomName") String roomName, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

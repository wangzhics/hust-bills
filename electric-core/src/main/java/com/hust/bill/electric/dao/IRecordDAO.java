package com.hust.bill.electric.dao;

import java.math.BigInteger;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.record.RecordTaskBean;
import com.hust.bill.electric.bean.task.record.RecordTaskResultBean;

@Repository(value="recordDAO")
public interface IRecordDAO {
	
	public void insertTask(RecordTaskBean taskBean);
	
	public void updateTaskEndTime(@Param("id") BigInteger id);
	
	public void updateTaskResultCount(@Param("id") BigInteger id);
	
	public void updateTaskSatus(@Param("id") BigInteger id, @Param("status") TaskStatus taskStatus);
	
	public BigInteger getTaskIDByName(@Param("name") String name);
	
	public RecordTaskBean[] getAllTask();
	
	public TaskBean getTaskById(@Param("id") BigInteger id);
	
	public void insertTaskResult(RecordTaskResultBean scanResult);
	
	public Building[] getUnSuccessBuildings(@Param("taskId") BigInteger taskId);
	
	public RecordTaskResultBean[] getTaskResultsByTaskID(@Param("taskID") BigInteger taskID);
	
	
	public RemainRecord[] getLastRemainsByBuilding(@Param("buildingName") String buildingName);
	
	public ChargeRecord[] getLastChargesByBuilding(@Param("buildingName") String buildingName);
	
	public void updateLastRemainsByBuilding(RemainRecord[] remainRecords);
	
	public void updateLastChargesByBuilding(ChargeRecord[] chargeRecords);
	
	public void insertRemains(RemainRecord[] remainRecords);
	
	public void insertCharges(ChargeRecord[] chargeRecords);
	
	
	public RemainRecord[] getUnCalculateRemains(@Param("buildingName") String buildingName, @Param("roomName") String roomName, @Param("lastDateTime") Date lastDateTime);
	
	public ChargeRecord[] getChargesByGapDate(@Param("buildingName") String buildingName, @Param("roomName") String roomName, @Param("startDateTime") Date startDateTime, @Param("endDateTime") Date endDateTime);

	
	public RemainRecord[] getRemainsByRoom(@Param("buildingName") String buildingName, @Param("roomName") String roomName, @Param("limit") int limit, @Param("offset") int offset);
	
	public int getRemainCountByRoom(@Param("buildingName") String buildingName, @Param("roomName") String roomName);
	
	public ChargeRecord[] getChargesByRoom(@Param("buildingName") String buildingName, @Param("roomName") String roomName, @Param("limit") int limit, @Param("offset") int offset);
	
	public int getChargeCountByRoom(@Param("buildingName") String buildingName, @Param("roomName") String roomName);
	
	
	public RemainRecord getLastRemainByRoom(@Param("buildingName") String buildingName, @Param("roomName") String roomName);
	
}

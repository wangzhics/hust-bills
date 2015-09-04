package com.hust.bill.electric.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
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
	
	public void insertTaskResult(RecordTaskResultBean scanResults);
	
	public RecordTaskResultBean[] getTaskResultsByTaskID(@Param("taskID") BigInteger taskID);
	
	public Map<String, Date> getLastRemainsByBuilding(@Param("buildingName") String buildingName);
	
	public Map<String, Date> getLastChargesByBuilding(@Param("buildingName") String buildingName);
	
	public void insertRemains(RemainRecord[] remainRecords);
	
	
	public void insertCharges(ChargeRecord[] chargeRecords);
	
}

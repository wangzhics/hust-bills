package com.hust.bill.electric.dao;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Param;

import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskBean;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;

public interface IConsumeDAO {
	
	public void insertTask(ConsumeTaskBean taskBean);
	
	public void updateTaskEndTime(@Param("id") BigInteger id);
	
	public void updateTaskResultCount(@Param("id") BigInteger id);
	
	public void updateTaskSatus(@Param("id") BigInteger id, @Param("status") TaskStatus taskStatus);
	
	public BigInteger getTaskIDByName(@Param("name") String name);
	
	public void insertTaskResult(ConsumeTaskResultBean scanResult);
	
	public void initialConsumeLast();
	
	public Consume[] getLastsByBuilding();
	
	public void updateLastsByBuilding(Consume[] consumes);
	
	public void insertConsumes(Consume[] consumes);
}

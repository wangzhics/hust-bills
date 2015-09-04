package com.hust.bill.electric.dao;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.room.RoomOperateBean;
import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;

@Repository(value="roomTempDAO")
public interface IRoomDAO {
	public void insertTask(RoomTaskBean taskBean);
	
	public void updateTaskSatus(@Param("id") BigInteger id, @Param("status") TaskStatus taskStatus);
	
	public void updateTaskEndTime(@Param("id") BigInteger id);
	
	public void updateTaskResultCount(@Param("id") BigInteger id);
	
	public BigInteger getTaskIDByName(@Param("name") String name);
	
	public RoomTaskBean[] getAllTask();
	
	public void insertTaskResults(RoomTaskResultBean[] taskResults);
	
	public RoomTaskResultBean[] getTaskResultsByTaskID(@Param("taskID") BigInteger taskID);
	
	public void insertOperateBeans(RoomOperateBean[] operateBeans);
	
	public RoomOperateBean[] getOperateBeansByTaskID(@Param("taskID") BigInteger taskID);
	
	public RoomOperateBean[] getAllOperateBeans();
	
	public void inserts(Room rooms[]);
	
	public Building[] getAll();
	
	public Room[] getByBuilding(@Param("buildingName") String buildingName);

	
}

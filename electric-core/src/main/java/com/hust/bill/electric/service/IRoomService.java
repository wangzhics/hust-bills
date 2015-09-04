package com.hust.bill.electric.service;

import java.math.BigInteger;

import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.room.RoomOperateBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;

public interface IRoomService extends ITaskService {
	
	public void addTaskResults(RoomTaskResultBean[] taskResults);
	
	public RoomTaskResultBean[] getTaskResultsByTaskID(BigInteger taskID);
	
	public void operate(RoomOperateBean[] operateBeans);
	
	public RoomOperateBean[] getAllOperation();
	
	public Room[] getAll();
	
	public Room[] getByBuilding(String buildingName);
}

package com.hust.bill.electric.service;

import java.math.BigInteger;

import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.room.RoomOperateBean;
import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;

public interface IRoomService {

	public void addTask(RoomTaskBean taskBean);
	
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus);
	
	public void finishTask(BigInteger taskId, TaskStatus taskStatus);
	
	public RoomTaskBean[] getAllTask();
	
	public void addTaskResults(RoomTaskResultBean[] taskResults);
	
	public RoomTaskResultBean[] getTaskResultsByTaskID(BigInteger taskID);
	
	public void operate(RoomOperateBean[] operateBeans);
	
	public RoomOperateBean[] getAllOperation();
	
	public Room[] getAll();
}

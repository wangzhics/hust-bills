package com.hust.bill.electric.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.room.RoomOperateBean;
import com.hust.bill.electric.bean.task.room.RoomTaskBean;
import com.hust.bill.electric.bean.task.room.RoomTaskResultBean;
import com.hust.bill.electric.dao.IRoomDAO;
import com.hust.bill.electric.service.IRoomService;

@Service(value="roomService")
public class RoomServiceImpl implements IRoomService {

	@Autowired
	private IRoomDAO roomDAO;
	
	
	@Override
	public void addTask(TaskBean taskBean) {
		RoomTaskBean roomTaskBean = (RoomTaskBean) taskBean;
		roomDAO.insertTask(roomTaskBean);
		taskBean.setId(roomDAO.getTaskIDByName(taskBean.getName()));
	}

	@Override
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus) {
		roomDAO.updateTaskSatus(taskId, taskStatus);
	}

	@Override
	@Transactional
	public void finishTask(BigInteger taskId, TaskStatus taskStatus) {
		roomDAO.updateTaskEndTime(taskId);
		roomDAO.updateTaskResultCount(taskId);
		roomDAO.updateTaskSatus(taskId, taskStatus);
	}

	@Override
	public RoomTaskBean[] getAllTask() {
		return roomDAO.getAllTask();
	}

	@Override
	public TaskBean getTaskById(BigInteger id) {
		return null;
	}
	
	@Override
	public void addTaskResults(RoomTaskResultBean[] taskResults) {
		roomDAO.insertTaskResults(taskResults);
	}

	@Override
	public RoomTaskResultBean[] getTaskResultsByTaskID(BigInteger taskID) {
		return roomDAO.getTaskResultsByTaskID(taskID);
	}

	@Override
	@Transactional
	public void operate(RoomOperateBean[] operateBeans) {
		List<RoomOperateBean> operateAddList = new ArrayList<RoomOperateBean>(operateBeans.length);
		List<Room> addList = new ArrayList<Room>(operateBeans.length);
		List<RoomOperateBean> operateUpdateList = new ArrayList<RoomOperateBean>(operateBeans.length);
		List<Room> updateList = new ArrayList<Room>(operateBeans.length);
		List<RoomOperateBean> operateDeleteList = new ArrayList<RoomOperateBean>(operateBeans.length);
		List<Room> deleteList = new ArrayList<Room>(operateBeans.length);
		for(RoomOperateBean operateBean : operateBeans) {
			switch (operateBean.getOperate()) {
			case ADD:
				operateAddList.add(operateBean);
				addList.add(operateBean.newRoom());
				break;
			case UPDATE:
				operateUpdateList.add(operateBean);
				updateList.add(operateBean.newRoom());
				break;
			case DELETE:
				operateDeleteList.add(operateBean);
				deleteList.add(operateBean.newRoom());
				break;
			default:
				break;
			}
		}
		// just add
		roomDAO.insertOperateBeans(operateAddList.toArray(new RoomOperateBean[0]));
		roomDAO.inserts(addList.toArray(new Room[0]));
	}

	@Override
	public RoomOperateBean[] getAllOperation() {
		return null;
	}

	@Override
	public Room[] getAll() {
		return null;
	}

	@Override
	public Room[] getByBuilding(String buildingName) {
		return roomDAO.getByBuilding(buildingName);
	}

	@Override
	public Room getByNames(String buildingName, String roomName) {
		return roomDAO.getByNames(buildingName, roomName);
	}

}

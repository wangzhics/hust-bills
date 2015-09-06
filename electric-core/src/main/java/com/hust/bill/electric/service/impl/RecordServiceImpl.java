package com.hust.bill.electric.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.record.RecordTaskBean;
import com.hust.bill.electric.bean.task.record.RecordTaskResultBean;
import com.hust.bill.electric.dao.IRecordDAO;
import com.hust.bill.electric.service.IRecordService;


@Service(value="recordService")
public class RecordServiceImpl implements IRecordService{
	
	@Autowired
	private IRecordDAO recordDAO;

	@Override
	public void addTask(TaskBean taskBean) {
		RecordTaskBean recordTaskBean = (RecordTaskBean) taskBean;
		recordDAO.insertTask(recordTaskBean);
		BigInteger id = recordDAO.getTaskIDByName(taskBean.getName());
		taskBean.setId(id);
	}

	@Override
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus) {
		recordDAO.updateTaskSatus(taskId, taskStatus);
	}
	

	@Override
	@Transactional
	public void finishTask(BigInteger taskId, TaskStatus taskStatus) {
		recordDAO.updateTaskEndTime(taskId);
		recordDAO.updateTaskResultCount(taskId);
		recordDAO.updateTaskSatus(taskId, taskStatus);
	}

	@Override
	public TaskBean[] getAllTask() {
		return recordDAO.getAllTask();
	}

	@Override
	public TaskBean getTaskById(BigInteger id) {
		return recordDAO.getTaskById(id);
	}
	
	@Override
	@Transactional
	public void insertRecords(RecordTaskResultBean taskResultBean, RemainRecord[] remainRecords, ChargeRecord[] chargeRecords) {
		recordDAO.insertTaskResult(taskResultBean);
		recordDAO.insertRemains(remainRecords);
		recordDAO.insertCharges(chargeRecords);
	}

	@Override
	public Map<String, Date> getLastRemainsByBuilding(String buildingName) {
		RemainRecord[] records = recordDAO.getLastRemainsByBuilding(buildingName);
		Map<String, Date> map = new HashMap<>(records.length);
		for(RemainRecord record : records) {
			map.put(record.getRoomName(), record.getDateTime());
		}
		return map;
	}

	@Override
	public Map<String, Date> getLastChargesByBuilding(String buildingName) {
		ChargeRecord[] records= recordDAO.getLastChargesByBuilding(buildingName);
		Map<String, Date> map = new HashMap<>(records.length);
		for(ChargeRecord record : records) {
			map.put(record.getRoomName(), record.getDateTime());
		}
		return map;
	}

	@Override
	public RemainRecord[] getUnCalculateRemains(String buildingName, String roomName, Date lastDateTime) {
		return recordDAO.getUnCalculateRemains(buildingName, roomName, lastDateTime);
	}
	
	public Building[] getUnSuccessBuildings(BigInteger taskId) {
		return recordDAO.getUnSuccessBuildings(taskId);
	}

	@Override
	public ChargeRecord[] getCharges(String buildingName, String roomName, Date startDateTime, Date endDateTime) {
		return recordDAO.getCharges(buildingName, roomName, startDateTime, endDateTime);
	}

}

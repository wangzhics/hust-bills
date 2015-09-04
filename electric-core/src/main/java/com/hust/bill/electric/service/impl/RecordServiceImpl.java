package com.hust.bill.electric.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	}

	@Override
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus) {
		recordDAO.updateTaskSatus(taskId, taskStatus);
	}

	@Override
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
	@Transactional
	public void insertRecords(RecordTaskResultBean taskResultBean, RemainRecord[] remainRecords, ChargeRecord[] chargeRecords) {
		recordDAO.insertTaskResult(taskResultBean);
		recordDAO.insertRemains(remainRecords);
		recordDAO.insertCharges(chargeRecords);
	}

	@Override
	public Map<String, Date> getLastRemainsByBuilding(String buildingName) {
		return recordDAO.getLastRemainsByBuilding(buildingName);
	}

	@Override
	public Map<String, Date> getLastChargesByBuilding(String buildingName) {
		return recordDAO.getLastChargesByBuilding(buildingName);
	}

}

package com.hust.bill.electric.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskBean;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;
import com.hust.bill.electric.dao.IConsumeDAO;
import com.hust.bill.electric.service.IConsumeService;


@Service(value="ConsumeService")
public class ConsumeService implements IConsumeService {

	@Autowired
	private IConsumeDAO consumeDAO;
	
	@Override
	public void addTask(TaskBean taskBean) {
		ConsumeTaskBean consumeTaskBean = (ConsumeTaskBean)taskBean;
		consumeDAO.insertTask(consumeTaskBean);
		consumeTaskBean.setId(consumeDAO.getTaskIDByName(taskBean.getName()));
	}

	@Override
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus) {
		consumeDAO.updateTaskSatus(taskId, taskStatus);
	}

	@Override
	@Transactional
	public void finishTask(BigInteger taskId, TaskStatus taskStatus) {
		consumeDAO.updateTaskEndTime(taskId);
		consumeDAO.updateTaskResultCount(taskId);
		consumeDAO.updateTaskSatus(taskId, taskStatus);
	}

	@Override
	public TaskBean[] getAllTask() {
		return null;
	}

	@Override
	public Map<String, RemainRecord> getLastRemainsByBuilding(String buildingName) {
		RemainRecord[] remainRecords = consumeDAO.getLastRemainsByBuilding();
		Map<String, RemainRecord> map = new HashMap<String, RemainRecord>(remainRecords.length);
		for(RemainRecord remainRecord : remainRecords) {
			map.put(remainRecord.getRoomName(), remainRecord);
		}
		return map;
	}

	@Override
	@Transactional
	public void insertConsumes(ConsumeTaskResultBean taskResultBean, RemainRecord[] lastRemains, Consume[] consumes) {
		consumeDAO.insertTaskResult(taskResultBean);
		consumeDAO.updateLastRemainsByBuilding(lastRemains);
		consumeDAO.insertConsumes(consumes);
	}

}

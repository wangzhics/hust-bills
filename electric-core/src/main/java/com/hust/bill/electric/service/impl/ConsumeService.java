package com.hust.bill.electric.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;
import com.hust.bill.electric.service.IConsumeService;

public class ConsumeService implements IConsumeService {

	@Override
	public void addTask(TaskBean taskBean) {
		
	}

	@Override
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus) {
		
	}

	@Override
	public void finishTask(BigInteger taskId, TaskStatus taskStatus) {
		
	}

	@Override
	public TaskBean[] getAllTask() {
		return null;
	}

	@Override
	public Map<String, Date> getLastDatesByBuilding(String buildingName) {
		return null;
	}

	@Override
	public void insertRecords(ConsumeTaskResultBean taskResultBean, Consume[] lastConsumes, Consume[] consumes) {
		
	}

}

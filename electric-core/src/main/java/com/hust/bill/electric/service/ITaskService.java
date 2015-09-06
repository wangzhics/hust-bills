package com.hust.bill.electric.service;

import java.math.BigInteger;

import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;

public interface ITaskService {
	
	public void addTask(TaskBean taskBean);
	
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus);
	
	public void finishTask(BigInteger taskId, TaskStatus taskStatus);
	
	public TaskBean[] getAllTask();
	
	public TaskBean getTaskById(BigInteger id);
}

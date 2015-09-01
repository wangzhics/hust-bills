package com.hust.bill.electric.core.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.service.ITaskService;

public abstract class Task implements Runnable {
	
	private final static Logger logger = LoggerFactory.getLogger(Task.class);
	
	private volatile int steps = 1;
	private volatile int currentStep = 0;
	private volatile int perpareFinished = 0;

	protected TaskBean taskBean;
	protected ITaskService taskService;

	public Task(ITaskService taskService) {
		this.taskService = taskService;
	}
	
	public void create() throws Exception {
		this.taskBean = createTaskBean();
		taskBean.setStartTime(new Date());
		taskBean.setStatus(TaskStatus.PERPARE);
		taskService.addTask(taskBean);
		logger.debug("task[{}] create finish", getName());
	}
	
	protected abstract TaskBean createTaskBean();
	
	protected abstract int perpare() throws Exception;
	
	protected abstract void execute() throws Exception;
	
	protected abstract void saveToDataBase();
	
	@Override
	public void run() {
		logger.debug("task[{}] start running", getName());
		TaskManager.getInstance().addTask(this);
		taskBean.setStatus(TaskStatus.RUNNING);
		taskService.updateTaskStatus(taskBean.getId(), TaskStatus.RUNNING);
		try {
			
			steps = perpare() + 2;
			perpareFinished = 1;
			
			execute();
			
			saveToDataBase();
			stepIn();
			
			finishTask(TaskStatus.FINISH);
			stepIn();
			
		} catch (Exception e) {
			logger.error("task[{}] failed", getName() ,e);
			finishTask(TaskStatus.ERROR);
		} finally {
			TaskManager.getInstance().removeTask(this);
		}
	}
	
	private void finishTask(TaskStatus taskStatus) {
		logger.debug("task[{}] finish[{}]", getName(), taskStatus.getDescription());
		taskBean.setStatus(taskStatus);
		taskService.finishTask(taskBean.getId(), taskStatus);
	}
	
	protected synchronized void stepIn() {
		currentStep ++;
	}
	
	public String getName() {
		return taskBean.getName();
	}
	
	public TaskStatus getTaskStatus() {
		return taskBean.getStatus();
	}
	
	public float getProgress() {
		return (float) (0.1 * perpareFinished + 0.9  * perpareFinished * currentStep / steps);
	}
	
}

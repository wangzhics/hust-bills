package com.hust.bill.electric.core.task;

import java.util.HashMap;
import java.util.Map;

import com.hust.bill.electric.bean.task.TaskStatus;

public class TaskManager {
	
	private static final TaskManager single = new TaskManager(); 
	private Map<String, Task> taskMap = new HashMap<String, Task>(100);
	
	private TaskManager() {
		
	}
	
	public static TaskManager getInstance() {  
		return single;  
	}
	
	public void addTask(Task t) {
		taskMap.put(t.getName(), t);
	}
	
	public void removeTask(Task t) {
		taskMap.remove(t);
	}
	
	public float getTaskProgress(Task t) {
		if(t == null) {
			return 0;
		}
		if(t.getTaskStatus() != TaskStatus.RUNNING) {
			return 0;
		}
		return t.getProgress();
	}
	
	public float getTaskProgress(String taskName) {
		Task t = taskMap.get(taskName);
		return getTaskProgress(t);
	}

}

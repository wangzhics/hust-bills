package com.hust.bill.electric.core.task.consume;

import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.service.ITaskService;

public class ConsumeCalculateTask extends Task {

	public ConsumeCalculateTask(ITaskService taskService) {
		super(taskService);
	}

	@Override
	protected TaskBean createTaskBean() {
		return null;
	}

	@Override
	protected int perpare() throws Exception {
		return 0;
	}

	@Override
	protected void execute() throws Exception {
		
	}

	@Override
	protected void saveToDataBase() {
		
	}

}

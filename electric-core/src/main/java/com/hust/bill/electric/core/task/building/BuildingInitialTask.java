package com.hust.bill.electric.core.task.building;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.task.Operation;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.building.BuildingOperateBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;
import com.hust.bill.electric.service.IBuildingService;

public class BuildingInitialTask extends BuildingScanTask {

	private final static Logger logger = LoggerFactory.getLogger(BuildingInitialTask.class);
	public final static String INITIAL_TASK_NAME = "Initial-Building";
	
	public BuildingInitialTask(IBuildingService buildingService) {
		super(buildingService);
	}
	
	@Override
	protected TaskBean createTaskBean() {
		BuildingTaskBean taskBean = new BuildingTaskBean();
		taskBean.setName(INITIAL_TASK_NAME);
		return taskBean;
	}
	
	@Override
	protected void saveToDataBase() {
		
		super.saveToDataBase();
		
		BuildingTaskResultBean[] taskResultBeans = getTaskService().getTaskResultsByTaskID(taskBean.getId());
		BuildingOperateBean[] operareBeans = new BuildingOperateBean[taskResultBeans.length];
		int i = 0;
		for(BuildingTaskResultBean resultBean : taskResultBeans) {
			operareBeans[i] = BuildingOperateBean.newOperateBean(getTaskBean(), resultBean, Operation.ADD);
			i++;
		}
		getTaskService().operate(operareBeans);
		logger.debug("building scan task[{}]: save autoOperate[{}] to database finish", getName(), 

		operareBeans.length);
	}

}

package com.hust.bill.electric.service.impl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.task.TaskBean;
import com.hust.bill.electric.bean.task.TaskStatus;
import com.hust.bill.electric.bean.task.building.BuildingOperateBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskResultBean;
import com.hust.bill.electric.bean.task.building.BuildingTaskBean;
import com.hust.bill.electric.dao.IBuildingDAO;
import com.hust.bill.electric.service.IBuildingService;

@Service(value="buildingService")
public class BuildingServiceImpl implements IBuildingService {

	@Autowired
	private IBuildingDAO buildingDAO;
	
	
	@Override
	@Transactional
	public void addTask(TaskBean taskBean) {
		BuildingTaskBean buildingTaskBean = (BuildingTaskBean)taskBean;
		buildingDAO.insertTask(buildingTaskBean);
		BigInteger id = buildingDAO.getTaskIDByName(taskBean.getName());
		taskBean.setId(id);
	}
	
	
	@Override
	public void updateTaskStatus(BigInteger taskId, TaskStatus taskStatus) {
		buildingDAO.updateTaskSatus(taskId, taskStatus);
	}
	
	@Override
	@Transactional
	public void finishTask(BigInteger taskId, TaskStatus taskStatus) {
		buildingDAO.updateTaskEndTime(taskId);
		buildingDAO.updateTaskResultCount(taskId);
		buildingDAO.updateTaskSatus(taskId, taskStatus);
	}
	
	@Override
	public void addTaskResults(BuildingTaskResultBean[] results) {
		buildingDAO.insertTaskResults(results);
		
	}
	
	@Override
	public BuildingTaskBean[] getAllTask() {
		return buildingDAO.getAllTask();
	}
	
	@Override
	@Transactional
	public void operate(BuildingOperateBean[] operateBeans) {
		List<BuildingOperateBean> operateAddList = new ArrayList<BuildingOperateBean>(operateBeans.length);
		List<Building> addList = new ArrayList<Building>(operateBeans.length);
		List<BuildingOperateBean> operateUpdateList = new ArrayList<BuildingOperateBean>(operateBeans.length);
		List<Building> updateList = new ArrayList<Building>(operateBeans.length);
		List<BuildingOperateBean> operateDeleteList = new ArrayList<BuildingOperateBean>(operateBeans.length);
		List<Building> deleteList = new ArrayList<Building>(operateBeans.length);
		for(BuildingOperateBean operateBean : operateBeans) {
			switch (operateBean.getOperate()) {
			case ADD:
				operateAddList.add(operateBean);
				addList.add(operateBean.newBuilding());
				break;
			case UPDATE:
				operateUpdateList.add(operateBean);
				updateList.add(operateBean.newBuilding());
				break;
			case DELETE:
				operateDeleteList.add(operateBean);
				deleteList.add(operateBean.newBuilding());
				break;
			default:
				break;
			}
		}
		// just add
		buildingDAO.insertOperateBeans(operateAddList.toArray(new BuildingOperateBean[0]));
		buildingDAO.inserts(addList.toArray(new Building[0]));
	}
	
	@Override
	public BuildingTaskResultBean[] getTaskResultsByTaskID(BigInteger scanID) {
		return buildingDAO.getTaskResultsByTaskID(scanID);
	}

	@Override
	public BuildingOperateBean[] getAllOperation() {
		return buildingDAO.getAllOperateBeans();
	}
	
	@Override
	public Building[] getAll() {
		return buildingDAO.getAll();
	}

}

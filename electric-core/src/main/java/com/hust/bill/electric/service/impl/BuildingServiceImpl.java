package com.hust.bill.electric.service.impl;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.BuildingOperateBean;
import com.hust.bill.electric.bean.task.BuildingScanResultBean;
import com.hust.bill.electric.bean.task.BuildingScanTaskBean;
import com.hust.bill.electric.dao.IBuildingDAO;
import com.hust.bill.electric.dao.IChargeRecordDAO;
import com.hust.bill.electric.dao.IRemainRecordDAO;
import com.hust.bill.electric.dao.IRoomDAO;
import com.hust.bill.electric.service.IBuildingService;

@Service(value="buildingService")
public class BuildingServiceImpl implements IBuildingService{

	@Autowired
	private IBuildingDAO buildingDAO;
	
	@Autowired
	private IRoomDAO roomDAO;
	
	@Autowired
	private IRemainRecordDAO remainRecordDAO;
	
	@Autowired
	private IChargeRecordDAO chargeRecordDAO;
	
	
	@Override
	@Transactional
	public void addScanTask(BuildingScanTaskBean taskBean) {
		buildingDAO.insertScanTask(taskBean);
		BigInteger id = buildingDAO.getScanTaskByTime(taskBean.getStarTime());
		taskBean.setId(id);
	}
	
	@Override
	@Transactional
	public void finishScanTask(BuildingScanTaskBean taskBean, BuildingScanResultBean[] scanResults) {
		buildingDAO.insertScanResults(scanResults);
		buildingDAO.updateScanTaskEndTime(taskBean.getId(), taskBean.getEndTime());
	}
	
	
	@Override
	public BuildingScanTaskBean[] getAllScanTask() {
		return buildingDAO.getAllScanTask();
	}
	
	@Override
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
		buildingDAO.insertOperateBeans(addList.toArray(new BuildingOperateBean[0]));
		buildingDAO.insert(addList.toArray(new Building[0]));
	}
	
	@Override
	public BuildingScanResultBean[] getScanResultsByTaskID(BigInteger scanID) {
		return buildingDAO.getScanResultsByTask(scanID);
	}

	
	@Override
	public Building[] getAll() {
		return null;
	}
}

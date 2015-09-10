package com.hust.bill.electric.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.Map;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.task.record.RecordTaskResultBean;

public interface IRecordService extends ITaskService {
	
	public Map<String, Date> getLastRemainDatesByBuilding(String buildingName);
	
	public Map<String, Date> getLastChargeDatesByBuilding(String buildingName);
	
	public void insertRecords(RecordTaskResultBean taskResultBean, RemainRecord[] remainRecords, ChargeRecord[] chargeRecords);
	
	public Building[] getUnSuccessBuildings(BigInteger taskId);
	
	public RemainRecord[] getUnCalculateRemains(String buildingName, String roomName, Date lastDateTime);
	
	public ChargeRecord[] getCharges(String buildingName, String roomName, Date startDateTime, Date endDateTime);
	
	public RemainRecord[] getRemains(String buildingName, String roomName, int limit, int offset);
	
	public int getRemainCount(String buildingName, String roomName);
	
	public RemainRecord getLastRemain(String buildingName, String roomName);
	
	public ChargeRecord[] getCharges(String buildingName, String roomName, int limit, int offset);
	
	public int getChargeCount(String buildingName, String roomName);
}

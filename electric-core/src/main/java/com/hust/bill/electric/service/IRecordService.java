package com.hust.bill.electric.service;

import java.util.Date;
import java.util.Map;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.task.record.RecordTaskResultBean;

public interface IRecordService extends ITaskService {
	
	public Map<String, Date> getLastRemainsByBuilding(String buildingName);
	
	public Map<String, Date> getLastChargesByBuilding(String buildingName);
	
	public void insertRecords(RecordTaskResultBean taskResultBean, RemainRecord[] remainRecords, ChargeRecord[] chargeRecords);
}

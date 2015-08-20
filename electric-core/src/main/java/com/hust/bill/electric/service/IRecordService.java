package com.hust.bill.electric.service;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;

public interface IRecordService {
	
	public void clearTempRemains();
	
	public void clearTempCharges();

	public void insertTempRemains(RemainRecord[] remainRecords);
	
	public void insertTempCharges(ChargeRecord[] chargeRecords);
}

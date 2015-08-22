package com.hust.bill.electric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.dao.IChargeTempDAO;
import com.hust.bill.electric.dao.IRemainTempDAO;
import com.hust.bill.electric.service.IRecordService;


@Service(value="recordService")
public class RecordServiceImpl implements IRecordService{

	@Autowired
	private IChargeTempDAO chargeTempDAO;
	
	@Autowired
	private IRemainTempDAO remainTempDAO;
	
	public void addTempRemains(RemainRecord[] remainRecords) {
		remainTempDAO.insert(remainRecords);
	}

	public void addTempCharges(ChargeRecord[] chargeRecords) {
		chargeTempDAO.insert(chargeRecords);
	}

	public void clearTempRemains() {
		remainTempDAO.truncate();
	}

	public void clearTempCharges() {
		chargeTempDAO.truncate();
	}

}

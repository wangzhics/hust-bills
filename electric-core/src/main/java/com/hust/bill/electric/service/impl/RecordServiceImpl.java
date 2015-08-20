package com.hust.bill.electric.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

private static Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);
	
	@Autowired
	private IChargeTempDAO chargeTempDAO;
	
	@Autowired
	private IRemainTempDAO remainTempDAO;
	
	public void insertTempRemains(RemainRecord[] remainRecords) {
		try {
			remainTempDAO.insert(remainRecords);
		} catch (DataAccessException e) {
			System.out.println(remainRecords);
			e.printStackTrace();
		}
	}

	public void insertTempCharges(ChargeRecord[] chargeRecords) {
		try {
			chargeTempDAO.insert(chargeRecords);
		} catch (DataAccessException e) {
			System.out.println(chargeRecords);
			e.printStackTrace();
		}
		
	}

	public void clearTempRemains() {
		remainTempDAO.truncate();
	}

	public void clearTempCharges() {
		chargeTempDAO.truncate();
	}

}

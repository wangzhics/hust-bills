package com.hust.bill.electric.dao;

import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.ChargeRecord;

@Repository(value="chargeTempDAO")
public interface IChargeTempDAO {

	public void insert(ChargeRecord[] chargeRecords);
	
	public ChargeRecord[] getAll();
	
	public void truncate();
	
}

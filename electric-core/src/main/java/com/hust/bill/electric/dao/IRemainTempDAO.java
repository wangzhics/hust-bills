package com.hust.bill.electric.dao;

import org.springframework.stereotype.Repository;

import com.hust.bill.electric.bean.RemainRecord;

@Repository(value="remainTempDAO")
public interface IRemainTempDAO {

	public void insert(RemainRecord[] remainRecords);
	
	public RemainRecord[] getAll(); 
	
	public void truncate();
}

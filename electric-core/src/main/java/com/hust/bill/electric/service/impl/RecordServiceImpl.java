package com.hust.bill.electric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.dao.IChargeRecordDAO;
import com.hust.bill.electric.dao.IRemainRecordDAO;
import com.hust.bill.electric.service.IRecordService;


@Service(value="recordService")
public class RecordServiceImpl implements IRecordService{

}

package com.hust.bill.electric.core.task.record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.thread.SubThreadRunnable;



public class RecordScanRunnable extends SubThreadRunnable {

	private static Logger logger = LoggerFactory.getLogger(RecordScanRunnable.class);
	private SimpleDateFormat TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Building building;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private List<RemainRecord> remainList = new ArrayList<RemainRecord>(100);
	private List<ChargeRecord> rechargeList = new ArrayList<ChargeRecord>(100);
	
	public RecordScanRunnable(Building building) {
		super("[" + building.getName() + "] Record Scan Thread");
		this.building = building;
	}



	@Override
	public void execute() {
		try {
			perpare();
			logger.info("Try get remain records of [" + building.getName() + "]");
			for(int i = 1; i <= building.getFloor(); i ++) {
				tryOneFloor(i);
			}
			logger.info("Get remain records of [" + building.getName() + "] Finish");
		} catch (RequestException e) {
			e.printStackTrace();
		} 
		
	}
	
	private void perpare() throws RequestException {
		logger.info("Try perpare get records of [" + building.getName() + "]");
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.info("Perpare get records of [" + building.getName() + "] Finish");
		
	}
	
	private void tryOneFloor(int floor) throws RequestException {
		int continueFalse = 0;
		RecordPage recordPage = null;
		for(int room = 1; room < 100; room ++) {
			String roomName = RemainRecord.getRoomName(floor, room);
			logger.info("Try get records of [" + building.getName() + "-" + roomName + "]");
			RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, room);
			httpClient.executeRequest(recordRequest);
			recordPage = new RecordPage(httpClient.getCurrentDocument());
			if(recordPage.isErrorPage()) {
				continueFalse ++;
			} else {
				continueFalse = 0;
				//TODO
				/*
				try {
					Date lastRecordDate = TIME_FORMATER.parse(recordPage.getDateTimeStr());
					float remain = Float.parseFloat(recordPage.getRecordStr());
					RemainRecord remainRecord = new RemainRecord(building.getName(), roomName, lastRecordDate, remain);
					logger.info("Get records of [" + building.getName() + "-" + roomName + "] remain: " + remainRecord.getRemain());
					remainList.add(remainRecord);
					if(!StringUtils.isEmpty(recordPage.getLastChargeTimeStr())) {
						Date lastChargeDate = TIME_FORMATER.parse(recordPage.getDateTimeStr());
						float rechargePower = Float.parseFloat(recordPage.getLastChargePowerStr());
						float rechargeMoney = Float.parseFloat(recordPage.getLastChargeMoneyStr());
						logger.info("Get records of [" + building.getName() + "-" + roomName + "] lastCharge: " + remainRecord);
					}
				} catch (ParseException e) {
					logger.info("Remain records datetime of [" + building.getName() + "-" + roomName + "] is " + recordPage.getDateTimeStr() + " can not be parse");
				} catch (NumberFormatException e) {
					logger.info("Remain records of [" + building.getName() + "-" + roomName + "] is " + recordPage.getRecordStr() + " can not be parse");
				}
				*/
			}
			if(continueFalse >  3) {
				logger.info("try over 3 times, [" + building.getName() + "-" + floor + "] max room is " + (room - continueFalse));
				break;
			}
		}
	}
	
	

}

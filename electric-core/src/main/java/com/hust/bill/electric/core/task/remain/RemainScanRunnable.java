package com.hust.bill.electric.core.task.remain;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.page.RemainRecordPage;
import com.hust.bill.electric.core.thread.SubThreadRunnable;



public class RemainScanRunnable extends SubThreadRunnable {

	private static Logger logger = LoggerFactory.getLogger(RemainScanRunnable.class);
	private SimpleDateFormat TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Building building;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private List<RemainRecord> records = new ArrayList<RemainRecord>(100);
	
	public RemainScanRunnable(Building building) {
		super(building.getName());
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
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalThreadStateException e) {
			e.printStackTrace();
		}
		
	}
	
	private void perpare() throws IOException, ClientProtocolException {
		logger.info("Try perpare get remain records of [" + building.getName() + "]");
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.info("Perpare get remain records of [" + building.getName() + "] Finish");
		
	}
	
	private void tryOneFloor(int floor) throws IOException, ClientProtocolException {
		int continueFalse = 0;
		RemainRecordPage recordPage = null;
		for(int room = 1; room < 100; room ++) {
			String roomStr = RemainRecord.getRoomStr(floor, room);
			logger.info("Try get remain records of [" + building.getName() + "-" + roomStr + "]");
			RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, room);
			httpClient.executeRequest(recordRequest);
			recordPage = new RemainRecordPage(httpClient.getCurrentDocument());
			if(recordPage.isErrorPage()) {
				continueFalse ++;
			} else {
				continueFalse = 0;
				Date dateTime;
				try {
					dateTime = TIME_FORMATER.parse(recordPage.getDateTimeStr());
					float record = Float.parseFloat(recordPage.getRecordStr());
					RemainRecord remainRecord = new RemainRecord(building.getName(), floor, room, dateTime, record);
					logger.info("Get remain records of [" + building.getName() + "-" + roomStr + "]: " + remainRecord.getRemain());
					records.add(remainRecord);
				} catch (ParseException e) {
					logger.info("Remain records datetime of [" + building.getName() + "-" + roomStr + "] is " + recordPage.getDateTimeStr() + " can not be parse");
					e.printStackTrace();
				} catch (NumberFormatException e) {
					logger.info("Remain records of [" + building.getName() + "-" + roomStr + "] is " + recordPage.getRecordStr() + " can not be parse");
					e.printStackTrace();
				}
			}
			if(continueFalse >  3) {
				logger.info("try over 3 times, [" + building.getName() + "-" + floor + "] max room is " + (room - continueFalse));
				break;
			}
		}
	}
	
	

}

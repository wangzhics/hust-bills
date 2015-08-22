package com.hust.bill.electric.core.task.record;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.RecordChargeLine;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.page.RecordRemainLine;
import com.hust.bill.electric.service.IRecordService;


public class RecordScanCallable implements Callable<RecordScanCallableReturn>  {

	private static Logger logger = LoggerFactory.getLogger(RecordScanCallable.class);
	private SimpleDateFormat TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Building building;
	private IRecordService recordService;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private List<RemainRecord> remainList = new ArrayList<RemainRecord>(2000);
	private List<ChargeRecord> chargeList = new ArrayList<ChargeRecord>(200);
	
	public RecordScanCallable(Building building, IRecordService recordService) {
		this.building = building;
		this.recordService = recordService;
	}
	
	
	@Override
	public RecordScanCallableReturn call() throws Exception {
		
		perpare();
		
		for(int i = 1; i <= building.getFloor(); i ++) {
			tryOneFloor(i);
		}
		
		saveRecords();
		
		RecordScanCallableReturn callableReturn = new RecordScanCallableReturn(building.getName(), remainList.size(), remainList.size());
		logger.info("buildings[{}] remain record count is [{}]", building.getName(), remainList.size());
		logger.info("buildings[{}] charge record count is [{}]", building.getName(), chargeList.size());
		return callableReturn;
	}
	
	private void perpare() throws RequestException {
		logger.debug("try perpare get records of [{}]", building.getName());
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("perpare get records of [{}] Finish", building.getName());
	}
	
	private void tryOneFloor(int floor) throws RequestException {
		int continueFalse = 0;
		for(int room = 1; room < 100; room ++) {
			if(!tryOneRoom(floor, room)) {
				continueFalse++;
			}
			if(continueFalse >  3) {
				logger.warn("try over 3 times, building[{}] max room no of floor[{}] is {}", building.getName(), floor, (room - continueFalse));
				break;
			}
		}
	}
	
	private RecordPage getRecordPage(int floor, int roomNO) throws RequestException {
		String roomName = Room.getRoomName(floor, roomNO);
		logger.debug("try get records page of [{}-{}]", building.getName(), roomName);
		RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, roomNO);
		httpClient.executeRequest(recordRequest);
		return new RecordPage(httpClient.getCurrentDocument());
	}
	
	private boolean tryOneRoom(int floor, int roomNO) throws RequestException {
		String roomName = Room.getRoomName(floor, roomNO);
		logger.debug("try get records of [{}-{}]", building.getName(), roomName);
		RecordPage recordPage = getRecordPage(floor, roomNO);
		if(recordPage.isErrorPage()) {
			logger.debug("records of [{}-{}] do not exists", building.getName(), roomName);
			return false;
		} 
		
		for(RecordRemainLine remainLine : recordPage.getRemainLines()) {
			try {
				Date remainDate = TIME_FORMATER.parse(remainLine.getDateStr());
				float remain = Float.parseFloat(remainLine.getRemainStr());
				RemainRecord remainRecord = new RemainRecord(building.getName(), roomName, remainDate, remain);
				remainList.add(remainRecord);
			} catch (ParseException e) {
				logger.warn("remain date[{}] of [{}-{}] can not be parse", remainLine.getDateStr(), building.getName(), roomName);
			} catch (NumberFormatException e) {
				logger.warn("remain[{}] of [{}-{}] can not be parse", remainLine.getRemainStr(), building.getName(), roomName);
			}
		}
		
		logger.debug("charge record count of [{}-{}] is {}", building.getName(), roomName,recordPage.getChargeLines().length);
		for(RecordChargeLine chargeLine : recordPage.getChargeLines()) {
			try {
				Date chargeDate = TIME_FORMATER.parse(chargeLine.getDateStr());
				float power = Float.parseFloat(chargeLine.getPowerStr());
				float money = Float.parseFloat(chargeLine.getMoneyStr());
				ChargeRecord chargeRecord = new ChargeRecord(building.getName(), roomName, chargeDate, power, money);
				chargeList.add(chargeRecord);
			} catch (ParseException e) {
				logger.warn("charge date[{}] of [{}-{}] can not be parse", chargeLine.getDateStr(), building.getName(), roomName);
			} catch (NumberFormatException e) {
				logger.warn("charge[{}, {}] of [{}-{}] can not be parse", chargeLine.getMoneyStr(), chargeLine.getMoneyStr(), building.getName(), roomName);
			}
		}
		logger.debug("try get records of [{}-{}] success", building.getName(), roomName);
		return true;
	}
	
	private void saveRecords() {
		recordService.insertTempRemains(remainList.toArray(new RemainRecord[0]));
		recordService.insertTempCharges(chargeList.toArray(new ChargeRecord[0]));
	}

}

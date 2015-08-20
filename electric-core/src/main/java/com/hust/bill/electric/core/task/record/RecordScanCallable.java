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
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.http.RequestException;
import com.hust.bill.electric.core.page.RecordChargeLine;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.page.RecordRemainLine;


public class RecordScanCallable implements Callable<RecordScanCallableReturn>  {

	private static Logger logger = LoggerFactory.getLogger(RecordScanCallable.class);
	private SimpleDateFormat TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private Building building;
	
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private List<RemainRecord> remainList = new ArrayList<RemainRecord>(1000);
	private List<ChargeRecord> chargeList = new ArrayList<ChargeRecord>(200);
	
	public RecordScanCallable(Building building) {
		this.building = building;
	}
	
	
	@Override
	public RecordScanCallableReturn call() throws Exception {
		
		perpare();
		
		for(int i = 1; i <= building.getFloor(); i ++) {
			tryOneFloor(i);
		}
		
		RecordScanCallableReturn callableReturn = new RecordScanCallableReturn(remainList.size(), remainList.size());
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
		RecordPage recordPage = null;
		for(int room = 1; room < 100; room ++) {
			String roomName = RemainRecord.getRoomName(floor, room);
			logger.debug("try get records of [{}-{}]", building.getName(), roomName);
			RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), floor, room);
			httpClient.executeRequest(recordRequest);
			recordPage = new RecordPage(httpClient.getCurrentDocument());
			if(recordPage.isErrorPage()) {
				logger.debug("records of [{}-{}] do not exists", building.getName(), roomName);
				continueFalse ++;
			} else {
				continueFalse = 0;
				logger.debug("remain record count of [{}-{}] is {}", building.getName(), roomName, recordPage.getRemainLines().length);
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
			}
			if(continueFalse >  3) {
				logger.warn("try over 3 times, building[{}] max room no of floor[{}] is {}", building.getName(), floor, (room - continueFalse));
				break;
			}
		}
	}

}

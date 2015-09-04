package com.hust.bill.electric.core.task.record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.record.RecordTaskBean;
import com.hust.bill.electric.bean.task.record.RecordTaskResultBean;
import com.hust.bill.electric.core.http.BuildingFloorRequest;
import com.hust.bill.electric.core.http.BuildingNameRequest;
import com.hust.bill.electric.core.http.ElectricHttpClient;
import com.hust.bill.electric.core.http.RemainRecordRequest;
import com.hust.bill.electric.core.page.RecordChargeLine;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.page.RecordRemainLine;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

public class ScanByBuildingCallable implements Callable<ScanByBuildingResult> {

	private final static Logger logger = LoggerFactory.getLogger(ScanByBuildingCallable.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private RecordTaskBean taskBean;
	private Building building;
	private IRoomService roomService;
	private IRecordService recordService;
	
	private Room[] rooms;
	private Map<String, Date> lastRemianMap;
	private Map<String, Date> lastChargeMap;
	
	private List<RemainRecord> remianRecordList = new ArrayList<RemainRecord>(200);
	private List<ChargeRecord> chargeRecordList = new ArrayList<ChargeRecord>(100);
	
	
	
	public ScanByBuildingCallable(RecordTaskBean taskBean, Building building, 
			IRoomService roomService, IRecordService recordService) {
		this.taskBean = taskBean;
		this.building = building;
		this.roomService = roomService;
		this.recordService = recordService;
	}


	@Override
	public ScanByBuildingResult call() throws Exception {
		perpare();
		for(Room room : rooms) {
			logger.debug("record[{}]: get room{} record page", building.getName(), room.getRoomName());
			RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), room.getRoomFloor(), room.getRoomNO());
			httpClient.executeRequest(recordRequest);
			if(RecordPage.hasRecord(httpClient.getCurrentDocument())) {
				logger.debug("record[{}]: get room{} record page success", building.getName(), room.getRoomName());
			} else {
				logger.warn("record[{}]: get room{} record page failed", building.getName(), room.getRoomName());
				continue;
			} 
			RecordPage recordPage = new RecordPage(sdf);
			recordPage.parse(httpClient.getCurrentDocument());
			int remianCount = 0, chargeCount = 0;
			Date roomLastRemainDate = lastRemianMap.get(room.getRoomName());
			for(RecordRemainLine remainLine : recordPage.getRemainLines()) {
				if(roomLastRemainDate == null || remainLine.getDate().after(roomLastRemainDate)) {
					remianRecordList.add(new RemainRecord(building.getName(), room.getRoomName(), remainLine.getDate(), remainLine.getRemain()));
					remianCount ++;
				}
			}
			Date roomLastChargeDate = lastChargeMap.get(room.getRoomName());
			for(RecordChargeLine chargeLine : recordPage.getChargeLines()) {
				if(roomLastChargeDate == null ||chargeLine.getDate().after(roomLastChargeDate)) {
					chargeRecordList.add(new ChargeRecord(building.getName(), room.getRoomName(), chargeLine.getDate(), chargeLine.getPower(), chargeLine.getMoney()));
					chargeCount ++;
				}
			}
			logger.info("record[{}]: room{} remian count {}-{}, charge count {}-{}", building.getName(), room.getRoomName(), recordPage.getRemainLines().length, remianCount, recordPage.getChargeLines().length, chargeCount);
		}
		RecordTaskResultBean resultBean = new RecordTaskResultBean(taskBean.getId(), building.getName(), remianRecordList.size(), chargeRecordList.size());
		logger.info("record[{}]: remian count {}, charge count {}", building.getName(), resultBean.getRemainCount(), resultBean.getChargeCount());
		recordService.insertRecords(resultBean, remianRecordList.toArray(new RemainRecord[0]), chargeRecordList.toArray(new ChargeRecord[0]));
		return new ScanByBuildingResult(resultBean);
	}
	
	
	private void perpare() throws Exception {
		logger.debug("record[{}]: perpare httpClient", building.getName());
		httpClient.perpare();
		BuildingNameRequest buildingNameRequest = new BuildingNameRequest(building.getArea());
		httpClient.executeRequest(buildingNameRequest);
		BuildingFloorRequest buildingFloorRequest = new BuildingFloorRequest(building.getArea(), building.getName());
		httpClient.executeRequest(buildingFloorRequest);
		logger.debug("record[{}]: perpare httpClient finish",building.getName());
		
		logger.debug("record[{}]: perpare record history", building.getName());
		rooms = roomService.getByBuilding(building.getName());
		lastRemianMap = recordService.getLastRemainsByBuilding(building.getName());
		lastChargeMap = recordService.getLastChargesByBuilding(building.getName());
		logger.debug("record[{}]: perpare record history finish, room-{} lastRemianMap-{}, lastChargeMap", building.getName(), rooms.length, lastRemianMap.size(), lastChargeMap.size());
	}

}

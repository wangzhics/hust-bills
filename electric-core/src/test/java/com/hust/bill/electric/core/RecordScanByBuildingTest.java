package com.hust.bill.electric.core;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class RecordScanByBuildingTest {
	
	@Autowired 
	private IRoomService roomService;
	
	@Autowired 
	private IRecordService recordService;
	
	private final static Logger logger = LoggerFactory.getLogger(RecordScanByBuildingTest.class);
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private ElectricHttpClient httpClient = new ElectricHttpClient();
	
	private RecordTaskBean taskBean;
	private Building building;
	
	private Room[] rooms;
	private Map<String, RemainRecord> lastRemianMap;
	private Map<String, ChargeRecord> lastChargeMap;
	
	private List<RemainRecord> remianRecordList = new ArrayList<RemainRecord>(200);
	private List<RemainRecord> lastRemianRecordList = new ArrayList<RemainRecord>(100);
	private List<ChargeRecord> chargeRecordList = new ArrayList<ChargeRecord>(100);
	private List<ChargeRecord> lastChargeRecordList = new ArrayList<ChargeRecord>(100);	
	
	@Test
	public void test() {
		building = new Building("紫菘", "紫菘2栋", 6);
		BigInteger taskId = BigInteger.valueOf(57);
		taskBean = (RecordTaskBean) recordService.getTaskById(taskId);
		try {
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
			
			rooms = roomService.getByBuilding(building.getName());
			for(Room room : rooms) {
				logger.debug("record[{}]: get room{} record page", building.getName(), room.getRoomName());
				RemainRecordRequest recordRequest = new RemainRecordRequest(building.getArea(), building.getName(), room.getRoomFloor(), room.getRoomNO());
				httpClient.executeRequest(recordRequest);
				if(RecordPage.hasRecord(httpClient.getCurrentDocument())) {
					logger.debug("record[{}]: get room{} record page success", building.getName(), room.getRoomName());
				} else {
					logger.warn("record[{}]: get room{} record page failed", building.getName(), room.getRoomName());
				} 
				RecordPage recordPage = new RecordPage(sdf);
				recordPage.parse(httpClient.getCurrentDocument());
				int remianCount = 0, chargeCount = 0;
				
				RemainRecord roomLastRemain = lastRemianMap.get(room.getRoomName());
				RemainRecord lastRemianRecord = new RemainRecord(roomLastRemain.getBuildingName(), roomLastRemain.getRoomName(), roomLastRemain.getDateTime(), roomLastRemain.getRemain());
				for(RecordRemainLine remainLine : recordPage.getRemainLines()) {
					if(roomLastRemain.getDateTime() == null || remainLine.getDate().after(roomLastRemain.getDateTime())) {
						remianRecordList.add(new RemainRecord(building.getName(), room.getRoomName(), remainLine.getDate(), remainLine.getRemain()));
						remianCount ++;
						if(remainLine.getDate().after(lastRemianRecord.getDateTime())) {
							lastRemianRecord.setDateTime(remainLine.getDate());
							lastRemianRecord.setRemain(remainLine.getRemain());
						}
					}
				}
				lastRemianRecordList.add(lastRemianRecord);
				
				ChargeRecord roomLastCharge = lastChargeMap.get(room.getRoomName());
				ChargeRecord lastChargeRecord = new ChargeRecord(roomLastCharge.getBuildingName(), roomLastCharge.getRoomName(), roomLastCharge.getDateTime(), roomLastCharge.getChargePower(), roomLastCharge.getChargeMoney());
				for(RecordChargeLine chargeLine : recordPage.getChargeLines()) {
					if(roomLastCharge.getDateTime() == null ||chargeLine.getDate().after(roomLastCharge.getDateTime())) {
						chargeRecordList.add(new ChargeRecord(building.getName(), room.getRoomName(), chargeLine.getDate(), chargeLine.getPower(), chargeLine.getMoney()));
						chargeCount ++;
						if(chargeLine.getDate().after(lastRemianRecord.getDateTime())) {
							lastChargeRecord.setDateTime(chargeLine.getDate());
							lastChargeRecord.setChargePower(chargeLine.getPower());
							lastChargeRecord.setChargeMoney(chargeLine.getMoney());
						}
					}
				}
				lastChargeRecordList.add(lastChargeRecord);
				logger.debug("record[{}]: room{} remian count {}-{}, charge count {}-{}", building.getName(), room.getRoomName(), recordPage.getRemainLines().length, remianCount, recordPage.getChargeLines().length, chargeCount);
			}
			RecordTaskResultBean resultBean = new RecordTaskResultBean(taskBean.getId(), building.getName(), remianRecordList.size(), chargeRecordList.size());
			recordService.addRecords(resultBean, 
					lastRemianRecordList.toArray(new RemainRecord[0]), lastChargeRecordList.toArray(new ChargeRecord[0]),
					remianRecordList.toArray(new RemainRecord[0]), chargeRecordList.toArray(new ChargeRecord[0]));
			logger.info("record[{}]: remian {}-{}, charge {}-{} save finish", building.getName(), remianRecordList.size(), lastRemianRecordList.size(), chargeRecordList.size(), lastChargeRecordList.size());
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}

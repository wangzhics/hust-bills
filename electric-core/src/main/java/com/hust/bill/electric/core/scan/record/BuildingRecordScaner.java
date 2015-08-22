package com.hust.bill.electric.core.scan.record;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
import com.hust.bill.electric.core.page.PageParseException;
import com.hust.bill.electric.core.page.RecordChargeLine;
import com.hust.bill.electric.core.page.RecordPage;
import com.hust.bill.electric.core.page.RecordRemainLine;
import com.hust.bill.electric.service.IRecordService;


public class BuildingRecordScaner implements Callable<BuildingRecordScanerReturn>  {

	private static Logger logger = LoggerFactory.getLogger(BuildingRecordScaner.class);
	
	private Building building;
	private IRecordService recordService;
	private ExecutorService executorService;
	
	private List<Future<FloorRecordScanerReturn>> resultList = new ArrayList<Future<FloorRecordScanerReturn>>(10);
	
	private List<RemainRecord> remainList = new ArrayList<RemainRecord>(2000);
	private List<ChargeRecord> chargeList = new ArrayList<ChargeRecord>(200);
	private List<Room> unSuccessRoomList = new ArrayList<Room>(100);
	
	private BuildingRecordScanerReturn scanerReturn = new BuildingRecordScanerReturn();
	
	public BuildingRecordScaner(Building building, IRecordService recordService) {
		this.building = building;
		this.recordService = recordService;
		executorService =  Executors.newFixedThreadPool(building.getFloor());
	}
	
	
	@Override
	public BuildingRecordScanerReturn call() throws Exception {
		
		for(int i = 1; i <= building.getFloor(); i ++) {
			FloorRecordScaner floorRecordScaner = new FloorRecordScaner(i, building);
			Future<FloorRecordScanerReturn> result = executorService.submit(floorRecordScaner);
			resultList.add(result);
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated()) {
			Thread.sleep(1000);
		}
		
		for(Future<FloorRecordScanerReturn> result : resultList) {
			FloorRecordScanerReturn scanerReturn = result.get();
			remainList.addAll(scanerReturn.getRemainList());
			chargeList.addAll(scanerReturn.getChargeList());
			unSuccessRoomList.addAll(scanerReturn.getUnSuccessRoomList());
		}
		logger.info("buildings[{}] remain count is [{}], charge cout is[{}], unSuccessRoom count is{[]}", building.getName(), remainList.size(), unSuccessRoomList.size());
		
		saveRecords();
		
		scanerReturn.setRemainCount(remainList.size());
		scanerReturn.setChargeCount(chargeList.size());
		scanerReturn.setUnSuccessRoomCount(unSuccessRoomList.size());
		
		return scanerReturn;
	}
	
	
	private void saveRecords() {
		recordService.addTempRemains(remainList.toArray(new RemainRecord[0]));
		recordService.addTempCharges(chargeList.toArray(new ChargeRecord[0]));
	}

}

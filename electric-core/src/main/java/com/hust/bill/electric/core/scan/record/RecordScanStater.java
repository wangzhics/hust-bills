package com.hust.bill.electric.core.scan.record;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;

public class RecordScanStater extends Thread {
	
	private static Logger logger = LoggerFactory.getLogger(RecordScanStater.class);
	
	private ExecutorService executorService = Executors.newFixedThreadPool(1);
	private List<Future<BuildingRecordScanerReturn>> resultList = new ArrayList<Future<BuildingRecordScanerReturn>>(100);
	
	private IBuildingService buildingService;
	private IRecordService recordService;
	
	private int remainRecordCount = 0;
	private int chargeRecordCount = 0;

	public RecordScanStater(IBuildingService buildingService, IRecordService recordService) {
		super();
		setDaemon(true);
		setName("Record Scan Stater");
		this.buildingService = buildingService;
		this.recordService = recordService;
	}
	
	@Override
	public void run() {
		
		logger.debug("clear e_record_remain_temp");
		recordService.clearTempRemains();
		logger.debug("clear e_record_charge_temp");
		recordService.clearTempCharges();
		
		Building[] allBuiilding = buildingService.getAll();
		
		for(Building building : allBuiilding) {
			BuildingRecordScaner scanCallable = new BuildingRecordScaner(building, recordService);
			Future<BuildingRecordScanerReturn> result = executorService.submit(scanCallable);
			resultList.add(result);
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("InterruptedException, should not be occour", e);
			}
		}
		
		for(Future<BuildingRecordScanerReturn> result : resultList) {
			try {
				BuildingRecordScanerReturn callableReturn = result.get();
				remainRecordCount = remainRecordCount + callableReturn.getRemainCount();
				chargeRecordCount = chargeRecordCount + callableReturn.getChargeCount();
			} catch (InterruptedException e) {
				logger.error("InterruptedException, should not be occour", e);
			} catch (ExecutionException e) {
				logger.error("ExecutionException", e);
			}
		}
	}
	
}

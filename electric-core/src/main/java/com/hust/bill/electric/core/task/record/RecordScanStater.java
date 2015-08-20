package com.hust.bill.electric.core.task.record;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.service.IBuildingService;

public class RecordScanStater extends Thread {
	
	private IBuildingService buildingService;
	
	private int remainRecordCount = 0;
	private int chargeRecordCount = 0;
	
	ExecutorService executorService = Executors.newFixedThreadPool(5);
	List<Future<RecordScanCallableReturn>> resultList = new ArrayList<Future<RecordScanCallableReturn>>(100);

	public RecordScanStater(IBuildingService buildingService) {
		super();
		setDaemon(true);
		setName("Record Scan Stater");
		this.buildingService = buildingService;
	}
	
	@Override
	public void run() {
		Building[] allBuiilding = buildingService.getAll();
		
		for(Building building : allBuiilding) {
			RecordScanCallable scanCallable = new RecordScanCallable(building);
			Future<RecordScanCallableReturn> result = executorService.submit(scanCallable);
			
		}
		
		executorService.shutdown();
		while(!executorService.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(Future<RecordScanCallableReturn> result : resultList) {
			try {
				RecordScanCallableReturn callableReturn = result.get();
				remainRecordCount = remainRecordCount + callableReturn.getRemainCount();
				chargeRecordCount = chargeRecordCount + callableReturn.getChargeCount();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
}

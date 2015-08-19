package com.hust.bill.electric.core.task.record;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.thread.DispatchThread;
import com.hust.bill.electric.service.IBuildingService;

public class RecordScanStater extends Thread {
	
	private IBuildingService buildingService;
	
	private DispatchThread dispatchThread = new DispatchThread("Record Scan DispatchThread", 5);

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
			RecordScanRunnable subThread = new RecordScanRunnable(building);
			dispatchThread.addSubThread(subThread);
		}
		dispatchThread.start();
	}
	
}

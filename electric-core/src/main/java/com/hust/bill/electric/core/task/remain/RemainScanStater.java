package com.hust.bill.electric.core.task.remain;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.thread.DispatchThread;
import com.hust.bill.electric.service.IBuildingService;

public class RemainScanStater extends Thread {
	
	private IBuildingService buildingService;
	
	private DispatchThread dispatchThread = new DispatchThread("Record Scan Executor Dispatch Thread", 5);

	public RemainScanStater(IBuildingService buildingService) {
		super();
		setDaemon(true);
		setName("Remain Scan Stater");
		this.buildingService = buildingService;
	}
	
	@Override
	public void run() {
		Building[] allBuiilding = buildingService.getAll();
		for(Building building : allBuiilding) {
			RemainScanRunnable subThread = new RemainScanRunnable(building);
			dispatchThread.addSubThread(subThread);
		}
		dispatchThread.start();
	}
	
}

package com.hust.bill.electric.core.task.room;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.thread.DispatchThread;
import com.hust.bill.electric.service.IBuildingService;

public class RoomScanStater extends Thread {

	private IBuildingService buildingService;
	
	private DispatchThread dispatchThread = new DispatchThread("Record Scan Executor Dispatch Thread", 5);

	public RoomScanStater(IBuildingService buildingService) {
		super();
		setDaemon(true);
		setName("Record Scan Stater");
		this.buildingService = buildingService;
	}
	
	@Override
	public void run() {
		Building[] allBuiilding = buildingService.getAll();
		for(Building building : allBuiilding) {
			RoomScanRunnable subThread = new RoomScanRunnable(building);
			dispatchThread.addSubThread(subThread);
		}
		dispatchThread.start();
	}
}

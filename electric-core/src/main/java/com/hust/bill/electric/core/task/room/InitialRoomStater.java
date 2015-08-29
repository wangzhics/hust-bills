package com.hust.bill.electric.core.task.room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.task.Task;
import com.hust.bill.electric.core.task.building.ScanAllTask;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRoomService;

public class InitialRoomStater  Runnable {

	public final static String TASK_NAME = "Initial-Room";
	private final static Logger logger = LoggerFactory.getLogger(ScanAllTask.class);
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	private IBuildingService buildingService;
	private IRoomService roomService;
	
	
	public InitialRoomStater(IBuildingService buildingService, IRoomService roomService) {
		super(TASK_NAME);
		this.buildingService = buildingService;
		this.roomService = roomService;
	}

	@Override
	public void run() {
		Building[] buildings = buildingService.getAll();
		
	}

}

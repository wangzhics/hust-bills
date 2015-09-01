package com.hust.bill.electric.core.task.room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRoomService;

public class RoomInitialStater implements Runnable {

	private final static Logger logger = LoggerFactory.getLogger(RoomInitialStater.class);
	
	private IBuildingService buildingService;
	private IRoomService roomService;
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	
	
	public RoomInitialStater(IBuildingService buildingService, IRoomService roomService) {
		this.buildingService = buildingService;
		this.roomService = roomService;
	}


	@Override
	public void run() {
		logger.debug("room initial start");
		try {
			for(Building b : buildingService.getAll()) {
				RoomInitialTask initialTask = new RoomInitialTask(b, roomService);
				initialTask.create();
				executorService.submit(initialTask);
			}
		} catch (Exception e) {
			logger.error("room initial failed, can not create room scan task", e);
			return;
		}
		executorService.shutdown();  
        while (true) {  
            if (executorService.isTerminated()) {
            	break;
            }  
            try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				logger.error("room initial failed, thread interrupt error", e);
				return;
			}  
        } 
        logger.debug("room initial finish");
	}

}

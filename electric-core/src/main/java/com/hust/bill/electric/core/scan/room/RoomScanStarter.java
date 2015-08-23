package com.hust.bill.electric.core.scan.room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRoomService;

public class RoomScanStarter  implements Callable<RoomScanResult> {
	
	private static Logger logger = LoggerFactory.getLogger(RoomScanStarter.class);

	private IBuildingService buildingService;
	private IRoomService roomService;
	
	private ExecutorService executorService = Executors.newFixedThreadPool(5);
	private List<Future<BuildingRoomScanResult>> resultList = new ArrayList<Future<BuildingRoomScanResult>>(10);  
	private RoomScanResult scanResult = new RoomScanResult();
	
	public RoomScanStarter(IBuildingService buildingService, IRoomService roomService) {
		this.buildingService = buildingService;
		this.roomService = roomService;
	}
	
	@Override
	public RoomScanResult call() throws Exception {
		Building[] buildings = buildingService.getAll();
		for(Building building : buildings) {
			scanResult.getUnSuccessBuildingList().add(building);
			BuildingRoomScaner buildingRoomScaner = new BuildingRoomScaner(building);
			Future<BuildingRoomScanResult> result = executorService.submit(buildingRoomScaner);
			resultList.add(result);
		}
		
		for(Future<BuildingRoomScanResult> result : resultList) {
			try {
				BuildingRoomScanResult roomScanResult = result.get();
				scanResult.getUnSuccessBuildingList().remove(roomScanResult.getBuilding());
				scanResult.getSuccessBuildingList().add(roomScanResult.getBuilding());
				roomService.updateByBuilding(roomScanResult.getBuilding().getName(), roomScanResult.getRooms());
				
			} catch (ExecutionException e) {
				logger.error("building scan room failed", e);
				executorService.shutdownNow();
				return scanResult;
			}
		}
		return scanResult;
	}

}

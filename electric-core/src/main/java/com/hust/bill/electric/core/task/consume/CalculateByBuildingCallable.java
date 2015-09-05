package com.hust.bill.electric.core.task.consume;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskBean;
import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;
import com.hust.bill.electric.service.IConsumeService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

public class CalculateByBuildingCallable implements Callable<CalculateByBuildingResult> {

	private final static Logger logger = LoggerFactory.getLogger(CalculateByBuildingCallable.class);
	private final static long DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;
	
	private ConsumeTaskBean taskBean;
	private Building building;
	private IRoomService roomService;
	private IRecordService recordService;
	private IConsumeService consumeService;
	
	private Room[] rooms;
	private Map<String, RemainRecord> lastRemainMap;
	private List<Consume> consumeList = new ArrayList<Consume>(200);
	private List<RemainRecord> lastRemainList = new ArrayList<RemainRecord>(200);
	
	
	public CalculateByBuildingCallable(ConsumeTaskBean taskBean, Building building, IRoomService roomService,
			IRecordService recordService, IConsumeService consumeService) {
		this.taskBean = taskBean;
		this.building = building;
		this.recordService = recordService;
		this.roomService = roomService;
		this.consumeService = consumeService;
	}



	@Override
	public CalculateByBuildingResult call() throws Exception {
		perpare();
		for(Room room : rooms) {
			RemainRecord lastRemainRecord = lastRemainMap.get(room.getRoomName());
			int startIndex = 0;
			RemainRecord[] unCalculateRemainRecords;
			if(lastRemainRecord == null){
				unCalculateRemainRecords = recordService.getUnCalculateRemains(room.getBuildingName(), room.getRoomName(), null);
				if(unCalculateRemainRecords.length == 0) {
					continue;
				}
				lastRemainRecord = unCalculateRemainRecords[0];
				startIndex = 1;
			} else {
				unCalculateRemainRecords = recordService.getUnCalculateRemains(room.getBuildingName(), room.getRoomName(), lastRemainRecord.getDateTime());
				if(unCalculateRemainRecords.length == 0) {
					continue;
				}
			}
			int consoumeCount = 0;
			for(; startIndex< unCalculateRemainRecords.length; startIndex ++) {
				RemainRecord indexRemainRecord = unCalculateRemainRecords[startIndex];
				long gapMillisecond = indexRemainRecord.getDateTime().getTime() - lastRemainRecord.getDateTime().getTime();
				int gapDays = (int)Math.rint(gapMillisecond / DAY_MILLI_SECONDS);
				if(gapDays == 0) {
					logger.warn("consume[{}]: room[{}] gap day of {} and {} is {}", building.getName(), room.getRoomName(), indexRemainRecord.getDateTime(), lastRemainRecord.getDateTime(), 0);
					lastRemainRecord = indexRemainRecord;
					continue;
				}
				if(gapDays > 3) {
					logger.warn("consume[{}]: room[{}] gap day of {} and {} is {}", building.getName(), room.getRoomName(), indexRemainRecord.getDateTime(), lastRemainRecord.getDateTime(), gapDays);
					lastRemainRecord = indexRemainRecord;
					continue;
				}
				float evenCounsume = 0, lastRemain = lastRemainRecord.getRemain(), indexRemain = indexRemainRecord.getRemain();
				if(indexRemain > lastRemain) {
					ChargeRecord[] chargeRecords = recordService.getCharges(room.getBuildingName(), room.getRoomName(), lastRemainRecord.getDateTime(), indexRemainRecord.getDateTime());
					for(ChargeRecord chargeRecord : chargeRecords) {
						lastRemain = lastRemain + chargeRecord.getChargePower();
					}
				}
				evenCounsume = (lastRemain - indexRemain) / gapDays;
				for(int i = 0; i < gapDays; i++) {
					Calendar gapCalender = Calendar.getInstance();
					gapCalender.setTime(lastRemainRecord.getDateTime());
					gapCalender.add(Calendar.DATE, i);
					consumeList.add(new Consume(building.getName(), room.getRoomName(), gapCalender.getTime(), evenCounsume));
					consoumeCount ++;
				}
				lastRemainRecord = indexRemainRecord;
			}
			lastRemainList.add(unCalculateRemainRecords[unCalculateRemainRecords.length - 1]);
			logger.debug("consume[{}]: room[{}] consume count {}", building.getName(), room.getRoomName(), consoumeCount);
		}
		ConsumeTaskResultBean resultBean = new ConsumeTaskResultBean(taskBean.getId(), building.getName(), consumeList.size());
		logger.info("consume[{}]: consume count {} ", building.getName(), resultBean.getConsumeCount());
		consumeService.insertConsumes(resultBean, lastRemainList.toArray(new RemainRecord[0]) , consumeList.toArray(new Consume[0]));
		return new CalculateByBuildingResult(resultBean);
	}
	
	private void perpare() throws Exception {
		logger.debug("consume[{}]: perpare record history", building.getName());
		rooms = roomService.getByBuilding(building.getName());
		lastRemainMap = consumeService.getLastRemainsByBuilding(building.getName());
		logger.debug("consume[{}]: perpare consume history finish, room-{} lastRemainMap-{}", building.getName(), rooms.length, lastRemainMap.size());
	}

}

package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.core.task.BasicSegmentConext;
import com.hust.bill.electric.core.task.AbstractExecutor;
import com.hust.bill.electric.service.IBuildingService;

@Component
public class UpdateExecutor extends AbstractExecutor {
	
	private static Logger logger = LoggerFactory.getLogger(AbstractExecutor.class);
	
	public UpdateExecutor(BasicSegmentConext chainConext) {
		super(chainConext);
		setName("Building[" + getSegmentConext().getArea() + "] Scan Executor Thread");
	}
	
	public UpdateSegmentConext getSegmentConext() {
		return (UpdateSegmentConext) segmentConext;
	}

	@Override
	protected void finish() {
		Building[] buildings = getSegmentConext().getBuildings().toArray(new Building[0]);
		IBuildingService buildingService =  getSegmentConext().getBuildingService();
		logger.info("Update Building[" + getSegmentConext().getArea() + "] Is " + buildings.toString());
		buildingService.update(buildings);
	}

	@Override
	protected void perpare() {
		try {
			segmentConext.getHttpClient().perpare();
			BuildingSegment buildingSegment = new BuildingSegment(getSegmentConext());
			segmentConext.getTaskSegments().add(buildingSegment);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

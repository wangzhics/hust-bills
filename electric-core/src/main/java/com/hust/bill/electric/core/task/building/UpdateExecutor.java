package com.hust.bill.electric.core.task.building;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.hust.bill.electric.core.task.ChainConext;
import com.hust.bill.electric.core.task.ChainExecutor;

public class UpdateExecutor extends ChainExecutor {
	
	private String area;
	
	public UpdateExecutor(ChainConext chainConext, String area) {
		super(chainConext);
		this.area = area;
	}

	@Override
	protected void finish() {
		UpdateConext updateConext = (UpdateConext) chainConext;
		System.out.println(updateConext.getBuildings());
	}

	@Override
	protected void perpare() {
		try {
			chainConext.getHttpClient().perpare();
			BuildingSegment buildingSegment = new BuildingSegment(area);
			chainConext.getTaskSegments().add(buildingSegment);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

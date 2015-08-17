package com.hust.bill.electric.core.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class BuildingFloorRequest implements IRequest {

	private String area;
	private String building;
	
	public BuildingFloorRequest(String area, String building) {
		this.area = area;
		this.building = building;
	}
	
	@Override
	public NameValuePair[] perparePostForm() {
		return new BasicNameValuePair[]{
			new BasicNameValuePair(HttpElements._PROGRAMID, area),
			new BasicNameValuePair(HttpElements._TXTYQ, building),
		};
	}

}

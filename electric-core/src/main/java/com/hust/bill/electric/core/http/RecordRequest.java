package com.hust.bill.electric.core.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class RecordRequest implements IRequest {
	
	private String area;
	private String building;
	private String room;

	public RecordRequest(String area, String building, String room) {
		this.area = area;
		this.building = building;
		this.room = room;
	}
	
	@Override
	public NameValuePair[] perparePostForm() {
		String floorStr = room.substring(0, 1);
		floorStr = floorStr + "层";
		return new BasicNameValuePair[]{
			new BasicNameValuePair(HttpElements._PROGRAMID, area),
			new BasicNameValuePair(HttpElements._TXTYQ, building),
			new BasicNameValuePair(HttpElements._TXTLD, floorStr),
			new BasicNameValuePair(HttpElements._TXTROOM, room),
		};
	}

}

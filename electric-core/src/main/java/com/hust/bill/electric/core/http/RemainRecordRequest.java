package com.hust.bill.electric.core.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.hust.bill.electric.bean.RemainRecord;

public class RemainRecordRequest implements IRequest {
	
	
	
	private String area;
	private String building;
	private int floor;
	private int room;

	public RemainRecordRequest(String area, String building, int floor, int room) {
		this.area = area;
		this.building = building;
		this.floor = floor;
		this.room = room;
	}
	
	@Override
	public NameValuePair[] perparePostForm() {
		String floorStr = floor + "层";
		String roomStr = RemainRecord.getRoomName(floor, room);
		return new BasicNameValuePair[]{
			new BasicNameValuePair(HttpElements._PROGRAMID, area),
			new BasicNameValuePair(HttpElements._TXTYQ, building),
			new BasicNameValuePair(HttpElements._TXTLD, floorStr),
			new BasicNameValuePair(HttpElements._TXTROOM, roomStr),
			new BasicNameValuePair(HttpElements._ImageButtonX, "55"),
			new BasicNameValuePair(HttpElements._ImageButtonY, "6"),
		};
	}

}

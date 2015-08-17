package com.hust.bill.electric.core.http;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class BuildingNameRequest implements IRequest {

	private String area;
	
	public BuildingNameRequest(String area) {
		this.area = area;
	}
	
	@Override
	public NameValuePair[] perparePostForm() {
		return new BasicNameValuePair[]{
			new BasicNameValuePair(HttpElements._PROGRAMID, area),
		};
	}

}

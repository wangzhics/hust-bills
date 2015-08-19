package com.hust.bill.electric.core.page;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hust.bill.electric.core.http.HttpElements;

public class RemainRecordPage extends AbstactPage {

	
	private String area;
	
	private String buildingName;
	
	private String floor;
	
	private String roomName;
	
	private String dateTimeStr;
	
	private String recordStr;
	
	public RemainRecordPage(Document doc) {
		super(doc);
		Element areaSelectElement = doc.getElementById(HttpElements._PROGRAMID);
		Element areaOptionElement = areaSelectElement.select("option[selected=selected]").first();
		area = areaOptionElement.val();
		
		Element buildingNameSelectElement = doc.getElementById(HttpElements._TXTYQ);
		Element buildingNameOptionElement = buildingNameSelectElement.select("option[selected=selected]").first();
		buildingName = buildingNameOptionElement.val();
		
		Element buildingFloorSelectElement = doc.getElementById(HttpElements._TXTLD);
		Element buildingFloorOptionElement = buildingFloorSelectElement.select("option[selected=selected]").first();
		floor = buildingFloorOptionElement.val();
		
		Element roomNameInputElement = doc.getElementById(HttpElements._TXTROOM);
		roomName = roomNameInputElement.val();
		
		Element timestapInputElement = doc.getElementById(HttpElements._TextBox2);
		dateTimeStr = timestapInputElement.val();
		
		Element recordInputElement = doc.getElementById(HttpElements._TextBox3);
		recordStr = recordInputElement.val();
	}
	
	public String getArea() {
		return area;
	}

	public String getBuildingName() {
		return buildingName;
	}
	
	public String getFloor() {
		return floor;
	}

	public String getRoomName() {
		return roomName;
	}

	public String getRecordStr() {
		return recordStr;
	}
	
	public String getDateTimeStr() {
		return dateTimeStr;
	}
	
	public boolean isErrorPage() {
		Elements deferElement = doc.select("script[defer]");
		if(deferElement.size() > 0) {
			return true;
		}
		return false;
	}


}

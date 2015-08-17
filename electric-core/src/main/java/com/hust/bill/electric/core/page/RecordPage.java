package com.hust.bill.electric.core.page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.hust.bill.electric.core.http.HttpElements;

public class RecordPage implements IPage {

	private final static SimpleDateFormat TIME_FORMATER = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private String area;
	
	private String buildingName;
	
	private String roomName;
	
	private Date recordDate;
	
	private float record;
	
	public String getArea() {
		return area;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getRoomName() {
		return roomName;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public float getRecord() {
		return record;
	}

	@Override
	public void updateAttributes(Document doc) {
		Element areaSelectElement = doc.getElementById(HttpElements._PROGRAMID);
		Element areaOptionElement = areaSelectElement.select("option[selected=selected]").first();
		area = areaOptionElement.val();
		Element buildingNameSelectElement = doc.getElementById(HttpElements._TXTYQ);
		Element buildingNameOptionElement = buildingNameSelectElement.select("option[selected=selected]").first();
		buildingName = buildingNameOptionElement.val();
		Element roomNameInputElement = doc.getElementById(HttpElements._TXTROOM);
		roomName = roomNameInputElement.val();
		Element timestapInputElement = doc.getElementById(HttpElements._TextBox2);
		try {
			recordDate = TIME_FORMATER.parse(timestapInputElement.val());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Element recordInputElement = doc.getElementById(HttpElements._TextBox3);
		record = Float.parseFloat(recordInputElement.val());
	}

}

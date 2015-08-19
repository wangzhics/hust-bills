package com.hust.bill.electric.core.page;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hust.bill.electric.core.http.HttpElements;

public class RecordPage extends AbstactPage {

	
	private String area;
	
	private String buildingName;
	
	private String buildingFloorStr;
	
	private String roomName;
	
	private RemainRecordLine[] remainRecords;
	
	private ChargeRecordLine[] chargeRecords = new ChargeRecordLine[0];
	
	private boolean isErrorPage = false;
	
	public RecordPage(Document doc) {
		super(doc);
		
		Elements deferElement = doc.select("script[defer]");
		if(deferElement.size() > 0) {
			isErrorPage = true;
			return;
		}
		
		Element areaSelectElement = doc.getElementById(HttpElements._PROGRAMID);
		Element areaOptionElement = areaSelectElement.select("option[selected=selected]").first();
		area = areaOptionElement.val();
		
		Element buildingNameSelectElement = doc.getElementById(HttpElements._TXTYQ);
		Element buildingNameOptionElement = buildingNameSelectElement.select("option[selected=selected]").first();
		buildingName = buildingNameOptionElement.val();
		
		Element buildingFloorSelectElement = doc.getElementById(HttpElements._TXTLD);
		Element buildingFloorOptionElement = buildingFloorSelectElement.select("option[selected=selected]").first();
		buildingFloorStr = buildingFloorOptionElement.val();
		
		Element roomNameInputElement = doc.getElementById(HttpElements._TXTROOM);
		roomName = roomNameInputElement.val();
		
		Element chargeTableElement = doc.getElementById(HttpElements._GRIDVIEW1);
		if(chargeTableElement != null) {
			List<ChargeRecordLine> chargeList = new ArrayList<ChargeRecordLine>(10);
			Elements chargeTrElements = chargeTableElement.select("tr:gt(0)");
			for(Element chargeTrElement : chargeTrElements) {
				Elements chargeTdElements = chargeTrElement.children();
				ChargeRecordLine chargeRecordLine = new ChargeRecordLine(chargeTdElements.get(2).html(), chargeTdElements.get(0).html(), chargeTdElements.get(1).html());
				chargeList.add(chargeRecordLine);
			}
			chargeRecords = chargeList.toArray(new ChargeRecordLine[0]);
		}
		
		List<RemainRecordLine> remainList = new ArrayList<RemainRecordLine>(10);
		Element remainTableElement = doc.getElementById(HttpElements._GRIDVIEW2);
		Elements remainTrElements = remainTableElement.select("tr:gt(0)");
		for(Element remainTrElement : remainTrElements) {
			Elements remainTdElements = remainTrElement.children();
			RemainRecordLine remainRecordLine = new RemainRecordLine(remainTdElements.get(1).html(), remainTdElements.get(0).html());
			remainList.add(remainRecordLine);
		}
		remainRecords = remainList.toArray(new RemainRecordLine[0]);
	}	

	public String getArea() {
		return area;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public String getBuildingFloorStr() {
		return buildingFloorStr;
	}

	public String getRoomName() {
		return roomName;
	}

	public RemainRecordLine[] getRemainRecords() {
		return remainRecords;
	}

	public ChargeRecordLine[] getChargeRecords() {
		return chargeRecords;
	}

	public boolean isErrorPage() {
		return isErrorPage;
	}
	
	
}

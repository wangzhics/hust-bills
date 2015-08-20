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
	
	private RecordRemainLine[] remainLines = new RecordRemainLine[0];
	
	private RecordChargeLine[] chargeLines = new RecordChargeLine[0];
	
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
			List<RecordChargeLine> chargeList = new ArrayList<RecordChargeLine>(10);
			Elements chargeTrElements = chargeTableElement.select("tr:gt(0)");
			for(Element chargeTrElement : chargeTrElements) {
				Elements chargeTdElements = chargeTrElement.children();
				RecordChargeLine chargeRecordLine = new RecordChargeLine(chargeTdElements.get(2).html(), chargeTdElements.get(0).html(), chargeTdElements.get(1).html());
				chargeList.add(chargeRecordLine);
			}
			chargeLines = chargeList.toArray(new RecordChargeLine[0]);
		}
		
		List<RecordRemainLine> remainList = new ArrayList<RecordRemainLine>(10);
		Element remainTableElement = doc.getElementById(HttpElements._GRIDVIEW2);
		Elements remainTrElements = remainTableElement.select("tr:gt(0)");
		for(Element remainTrElement : remainTrElements) {
			Elements remainTdElements = remainTrElement.children();
			RecordRemainLine remainRecordLine = new RecordRemainLine(remainTdElements.get(1).html(), remainTdElements.get(0).html());
			remainList.add(remainRecordLine);
		}
		remainLines = remainList.toArray(new RecordRemainLine[0]);
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

	public RecordRemainLine[] getRemainLines() {
		return remainLines;
	}
	
	public RecordChargeLine[] getChargeLines() {
		return chargeLines;
	}

	public boolean isErrorPage() {
		return isErrorPage;
	}
	
	
}

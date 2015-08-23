package com.hust.bill.electric.core.page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hust.bill.electric.core.http.HttpElements;

public class RecordPage implements IPage {

	private SimpleDateFormat dateFormat;
	
	private String area;
	
	private String buildingName;
	
	private String buildingFloorStr;
	
	private String roomName;
	
	private RecordRemainLine[] remainLines = new RecordRemainLine[0];
	
	private RecordChargeLine[] chargeLines = new RecordChargeLine[0];
	
	private boolean hasRecord = false;
	
	public RecordPage(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public static boolean hasRecord(Document doc) {
		Elements deferElement = doc.select("script[defer]");
		if(deferElement.size() > 0) {
			return false;
		}
		return true;
	}
	
	public void parse(Document doc) throws PageParseException {
		
		Elements deferElement = doc.select("script[defer]");
		if(deferElement.size() > 0) {
			return;
		}
		
		hasRecord = true;
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
		
		List<RecordRemainLine> remainList = new ArrayList<RecordRemainLine>(10);
		Element remainTableElement = doc.getElementById(HttpElements._GRIDVIEW2);
		Elements remainTrElements = remainTableElement.select("tr:gt(0)");
		for(Element remainTrElement : remainTrElements) {
			Elements remainTdElements = remainTrElement.children();
			String remainStr = remainTdElements.get(0).html();
			String dateStr = remainTdElements.get(1).html();
			try {
				Date remainDate = dateFormat.parse(dateStr);
				float remain = Float.parseFloat(remainStr);
				RecordRemainLine remainRecordLine = new RecordRemainLine(remain, remainDate);
				remainList.add(remainRecordLine);
			} catch (ParseException e) {
				throw new PageParseException("room[" + buildingName + "-" + roomName + "] remain record date[" + dateStr + "] can not be parse", e);
			} catch (NumberFormatException e) {
				throw new PageParseException("room[" + buildingName + "-" + roomName + "] remain record value[" + remainStr + "] can not be parse", e);
			}
		}
		remainLines = remainList.toArray(new RecordRemainLine[0]);
		
		Element chargeTableElement = doc.getElementById(HttpElements._GRIDVIEW1);
		if(chargeTableElement != null) {
			List<RecordChargeLine> chargeList = new ArrayList<RecordChargeLine>(10);
			Elements chargeTrElements = chargeTableElement.select("tr:gt(0)");
			for(Element chargeTrElement : chargeTrElements) {
				Elements chargeTdElements = chargeTrElement.children();
				String powerStr = chargeTdElements.get(0).html();
				String moneyStr = chargeTdElements.get(1).html();
				String dateStr = chargeTdElements.get(2).html();
				try {
					float power = Float.parseFloat(powerStr);
					float money = Float.parseFloat(moneyStr);
					Date chargeDate = dateFormat.parse(dateStr);
					RecordChargeLine chargeRecordLine = new RecordChargeLine(power, money, chargeDate);
					chargeList.add(chargeRecordLine);
				} catch (ParseException e) {
					throw new PageParseException("room[" + buildingName + "-" + roomName + "] charge record date[" + dateStr + "] can not be parse", e);
				} catch (NumberFormatException e) {
					throw new PageParseException("room[" + buildingName + "-" + roomName + "] charge record value[" + powerStr + " or " + moneyStr + "] can not be parse", e);
				}
			}
			chargeLines = chargeList.toArray(new RecordChargeLine[0]);
		}
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

	public boolean hasRecord() {
		return hasRecord;
	}
	
	
}

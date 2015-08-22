package com.hust.bill.electric.core.page;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hust.bill.electric.core.http.HttpElements;

public class BuildingFloorPage implements IPage {
	
	private String area;
	
	private String buildingName;
	
	private int floor;

	public void parse(Document doc) throws PageParseException {
		Element areaSelectElement = doc.getElementById(HttpElements._PROGRAMID);
		Element areaOptionElement = areaSelectElement.select("option[selected=selected]").first();
		area = areaOptionElement.val();
		
		Element buildingNameSelectElement = doc.getElementById(HttpElements._TXTYQ);
		Element buildingNameOptionElement = buildingNameSelectElement.select("option[selected=selected]").first();
		buildingName = buildingNameOptionElement.val();
		
		floor = 0;
		Element selectElement = doc.getElementById(HttpElements._TXTLD);
		Elements optionElements = selectElement.children();
		for(Element optionElement : optionElements) {
			String value = optionElement.val();
			if(Character.isDigit(value.charAt(0))) {
				floor = floor + 1;
			}
		}
	}
	
	public String getArea() {
		return area;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public int getFloor() {
		return floor;
	}
}

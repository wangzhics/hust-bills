package com.hust.bill.electric.core.page;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hust.bill.electric.core.http.HttpElements;

public class BuildingFloorPage implements IPage {

	private String area;
	
	private String buildingName;
	
	private int floor;
	
	public String getArea() {
		return area;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public int getFloor() {
		return floor;
	}

	@Override
	public void updateAttributes(Document doc) {
		Element areaSelectElement = doc.getElementById(HttpElements._PROGRAMID);
		Element areaOptionElement = areaSelectElement.select("option[selected=selected]").first();
		area = areaOptionElement.val();
		Element buildingNameSelectElement = doc.getElementById(HttpElements._TXTYQ);
		Element buildingNameOptionElement = buildingNameSelectElement.select("option[selected=selected]").first();
		buildingName = buildingNameOptionElement.val();
		Element selectElement = doc.getElementById(HttpElements._TXTLD);
		Elements optionElements = selectElement.children();
		floor = optionElements.size() - 1;
	}

}

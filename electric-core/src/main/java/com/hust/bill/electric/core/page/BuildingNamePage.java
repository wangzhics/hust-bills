package com.hust.bill.electric.core.page;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hust.bill.electric.core.http.HttpElements;

public class BuildingNamePage extends AbstactPage {
	
	private String area;
	
	private String[] buildingNames;

	public BuildingNamePage(Document doc) {
		super(doc);
		Element areaSelectElement = doc.getElementById(HttpElements._PROGRAMID);
		Element areaOptionElement = areaSelectElement.select("option[selected=selected]").first();
		area = areaOptionElement.val();
		Element selectElement = doc.getElementById(HttpElements._TXTYQ);
		Elements optionElements = selectElement.children();
		List<String> buildingNameList = new ArrayList<String>(10);
		for(Element optionElement : optionElements) {
			String value = optionElement.val();
			if(value.equals("-1")) {
				continue;
			}
			buildingNameList.add(value);
		}
		buildingNames = buildingNameList.toArray(new String[0]);
	}
	
	public String getArea() {
		return area;
	}
	
	public String[] getBuildingNames() {
		return buildingNames;
	}

	
	
}

package com.hust.bill.electric.core.page;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.hust.bill.electric.core.http.HttpElements;

public class AreaPage implements IPage {
	
	private String[] areas;
	
	public AreaPage() {
		
	}

	public void parse(Document doc) throws PageParseException {
		Element areaSelectElement = doc.getElementById(HttpElements._PROGRAMID);
		Elements areaOptionElements = areaSelectElement.children();
		List<String> areaList = new ArrayList<String>(10);
		for(Element areaOptionElement : areaOptionElements) {
			String value = areaOptionElement.val();
			if(value.equals("-1")) {
				continue;
			}
			areaList.add(value);
		}
		areas = areaList.toArray(new String[0]);
	}
	
	public String[] getAreas() {
		return areas;
	}
	
}

package com.hust.bill.electric.core.page;

import org.jsoup.nodes.Document;

public interface IPage {
	
	public void parse(Document doc) throws PageParseException;
}

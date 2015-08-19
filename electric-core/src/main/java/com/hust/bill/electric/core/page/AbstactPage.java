package com.hust.bill.electric.core.page;

import org.jsoup.nodes.Document;

public abstract class AbstactPage {
	
	protected Document doc;
	
	public AbstactPage(Document doc) {
		this.doc = doc;
	}

}

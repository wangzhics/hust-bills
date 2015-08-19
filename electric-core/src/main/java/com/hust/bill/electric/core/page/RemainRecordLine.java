package com.hust.bill.electric.core.page;

public class RemainRecordLine {

	private String dateStr;
	
	private String remainStr;

	public RemainRecordLine() {
	}
	
	public RemainRecordLine(String dateStr, String remainStr) {
		this.dateStr = dateStr;
		this.remainStr = remainStr;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getRemainStr() {
		return remainStr;
	}

	public void setRemainStr(String remainStr) {
		this.remainStr = remainStr;
	}
	
}

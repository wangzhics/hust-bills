package com.hust.bill.electric.core.page;

public class ChargeRecordLine {

	private String dateStr;
	
	private String powerStr;
	
	private String moneyStr;

	public ChargeRecordLine() {
	}

	public ChargeRecordLine(String dateStr, String powerStr, String moneyStr) {
		this.dateStr = dateStr;
		this.powerStr = powerStr;
		this.moneyStr = moneyStr;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getPowerStr() {
		return powerStr;
	}

	public void setPowerStr(String powerStr) {
		this.powerStr = powerStr;
	}

	public String getMoneyStr() {
		return moneyStr;
	}

	public void setMoneyStr(String moneyStr) {
		this.moneyStr = moneyStr;
	}
	
}

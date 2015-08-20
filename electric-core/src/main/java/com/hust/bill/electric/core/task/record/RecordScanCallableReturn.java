package com.hust.bill.electric.core.task.record;

class RecordScanCallableReturn {

	private String buildingName;
	
	private int remainCount;
	
	private int chargeCount;

	public RecordScanCallableReturn(String buildingName, int remainCount, int chargeCount) {
		this.buildingName = buildingName;
		this.remainCount = remainCount;
		this.chargeCount = chargeCount;
	}
	
	public String getBuildingName() {
		return buildingName;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public int getChargeCount() {
		return chargeCount;
	}
	
}

package com.hust.bill.electric.core.scan.record;


class BuildingRecordScanerReturn {

	private String buildingName;
	private int remainCount;
	private int chargeCount;
	
	public BuildingRecordScanerReturn(String buildingName, int remainCount, int chargeCount) {
		this.buildingName = buildingName;
		this.remainCount = remainCount;
		this.chargeCount = chargeCount;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public void setChargeCount(int chargeCount) {
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

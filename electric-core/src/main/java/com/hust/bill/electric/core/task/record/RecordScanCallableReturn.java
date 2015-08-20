package com.hust.bill.electric.core.task.record;

class RecordScanCallableReturn {

	private int remainCount;
	
	private int chargeCount;

	public RecordScanCallableReturn(int remainCount, int chargeCount) {
		this.remainCount = remainCount;
		this.chargeCount = chargeCount;
	}

	public int getRemainCount() {
		return remainCount;
	}

	public void setRemainCount(int remainCount) {
		this.remainCount = remainCount;
	}

	public int getChargeCount() {
		return chargeCount;
	}

	public void setChargeCount(int chargeCount) {
		this.chargeCount = chargeCount;
	}
	
}

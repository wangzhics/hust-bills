package com.hust.bill.electric.core.page;

import java.util.Date;

public class RecordRemainLine {
	
	private float remain;
	
	private Date date;
	
	public RecordRemainLine(float remain, Date date) {
		this.remain = remain;
		this.date = date;
	}

	public float getRemain() {
		return remain;
	}
	
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return String.valueOf(remain);
	}
	
}

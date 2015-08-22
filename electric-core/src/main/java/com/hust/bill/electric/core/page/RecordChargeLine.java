package com.hust.bill.electric.core.page;

import java.util.Date;

public class RecordChargeLine {
	
	private float power;
	
	private float money;
	
	private Date date;

	public RecordChargeLine(float power, float money, Date date) {
		this.power = power;
		this.money = money;
		this.date = date;
	}

	public float getPower() {
		return power;
	}

	public float getMoney() {
		return money;
	}
	
	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "[power=" + power + ", money=" + money + "]";
	}
	
}

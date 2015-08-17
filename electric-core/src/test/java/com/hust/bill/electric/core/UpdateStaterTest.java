package com.hust.bill.electric.core;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hust.bill.electric.core.task.building.UpdateStater;

public class UpdateStaterTest {

	@Test
	public void test() {
		UpdateStater stater = new UpdateStater();
		stater.start();
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

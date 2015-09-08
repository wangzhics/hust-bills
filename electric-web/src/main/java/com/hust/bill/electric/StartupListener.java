package com.hust.bill.electric;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// 防止重复执行。
		System.out.println(event);
		System.out.println(event.getApplicationContext().getParent());
	}

}

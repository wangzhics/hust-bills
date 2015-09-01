package com.hust.bill.electric.core.task;

import com.hust.bill.electric.bean.task.TaskStatus;

public abstract class Task implements Runnable {

	private volatile int steps = 1;
	private volatile int currentStep = 0;
	private volatile int perpare = 0;
	private String currentMessage = "";

	public abstract String getName();
	
	public abstract TaskStatus getTaskStatus();
	
	protected void setSteps(int newSteps) {
		currentStep ++;
	}
	
	protected void finishPerpare(int steps) {
		this.perpare = 1;
		this.steps = steps;
		this.currentStep = 0;
	}
	
	protected synchronized void stepIn() {
		currentStep ++;
	}
	
	protected void setMessage(String msg) {
		this.currentMessage = msg;
	}
	
	public String getMessage() {
		return currentMessage;
	}
	
	public float getProgress() {
		return (float) (0.1 * perpare + 0.9  * perpare * currentStep / steps);
	}
	
}

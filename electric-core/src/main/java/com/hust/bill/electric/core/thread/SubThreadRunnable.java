package com.hust.bill.electric.core.thread;

public abstract class SubThreadRunnable implements Runnable {
	
	private ISubThreadFinishListener listener;

	private String threadName;
	
	public SubThreadRunnable(String threadName) {
		this.threadName = threadName;
	}
	
	@Override
	public void run() {
		execute();
		listener.notifyFinish();
	}
	
	public abstract void execute();
	
	public void setListener(ISubThreadFinishListener listener) {
		this.listener = listener;
	}
	
	public String getThreadName() {
		return threadName;
	}
}

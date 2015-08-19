package com.hust.bill.electric.core.thread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;


public class DispatchThread extends Thread implements ISubThreadFinishListener {

	private Queue<SubThreadRunnable> subThreads = new LinkedList<SubThreadRunnable>();
	
	private Semaphore threadSemaphore;
	
	public DispatchThread(String name, int subThreadMax) {
		super(name);
		setDaemon(true);
		threadSemaphore = new Semaphore(subThreadMax);
	}

	public void addSubThread(SubThreadRunnable subThread) {
		subThreads.add(subThread);
	}
	
	@Override
	public void run() {
		SubThreadRunnable subThreadRunnable = subThreads.poll();
		while(subThreadRunnable != null){
			try {
				threadSemaphore.acquire();
				Thread subThread = new Thread(subThreadRunnable);
				subThread.setDaemon(true);
				subThread.setName(subThreadRunnable.getThreadName());
				subThread.start();
				subThreadRunnable = subThreads.poll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void notifyFinish() {
		threadSemaphore.release();
	}
}

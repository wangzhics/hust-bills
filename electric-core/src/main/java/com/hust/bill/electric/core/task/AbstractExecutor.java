package com.hust.bill.electric.core.task;


public abstract class AbstractExecutor extends Thread  {
	
	protected BasicSegmentConext segmentConext;
	
	public AbstractExecutor(BasicSegmentConext segmentConext) {
		super();
		setDaemon(true);
		this.segmentConext = segmentConext;
	}

	@Override
	public void run() {
		perpare();
		BasicTaskSegment currentSegment = segmentConext.getTaskSegments().poll();
		while(currentSegment != null){
			currentSegment.execute();
			currentSegment = segmentConext.getTaskSegments().poll();
		}
		finish();
	}
	
	public BasicSegmentConext getSegmentConext() {
		return segmentConext;
	}
	
	protected abstract void perpare();
	
	protected abstract void finish();
}

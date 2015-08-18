package com.hust.bill.electric.core.task;


public abstract class BasicTaskSegment {

	private BasicSegmentConext segmentConext;
	
	public BasicTaskSegment(BasicSegmentConext segmentConext) {
		this.segmentConext = segmentConext;
	}
	
	public BasicSegmentConext getSegmentConext() {
		return segmentConext;
	}
	
	public abstract void execute();
	
}

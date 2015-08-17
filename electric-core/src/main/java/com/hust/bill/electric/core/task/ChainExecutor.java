package com.hust.bill.electric.core.task;


public abstract class ChainExecutor extends Thread  {
	
	protected ChainConext chainConext;
	
	public ChainExecutor(ChainConext chainConext) {
		this.chainConext = chainConext;
	}

	@Override
	public void run() {
		perpare();
		ITaskSegment currentSegment = chainConext.getTaskSegments().poll();
		while(currentSegment != null){
			currentSegment.execute(chainConext);
			currentSegment = chainConext.getTaskSegments().poll();
		}
		finish();
	}
	
	protected abstract void perpare();
	
	protected abstract void finish();
}

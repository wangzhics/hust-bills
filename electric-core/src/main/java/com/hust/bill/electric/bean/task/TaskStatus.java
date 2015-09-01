package com.hust.bill.electric.bean.task;

public enum TaskStatus {
	PERPARE(0, "准备"),
	RUNNING(1, "运行"), 
	FINISH(2, "完成"), 
	ERROR(3, "错误");

	private TaskStatus(int code, String description) {
		this.code = new Integer(code);
		this.description = description;
	}

	private int code;

	private String description;

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static TaskStatus parser(int code) {
		if(code == PERPARE.getCode()) {
			return PERPARE;
		} else if(code == RUNNING.getCode()) {
			return RUNNING;
		} else if(code == FINISH.getCode()) {
			return FINISH;
		} else if(code == ERROR.getCode()) {
			return RUNNING;
		} 
		return null;
	}
	
}

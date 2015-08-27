package com.hust.bill.electric.bean.task;

public enum Operation {
	ADD(1, "添加"), 
	UPDATE(2, "更新"), 
	DELETE(3, "删除");

	private Operation(int code, String description) {
		this.code = new Integer(code);
		this.description = description;
	}

	private Integer code;

	private String description;

	public Integer getCode() {
		return code;
	}

	public String getDescription() {

		return description;
	}

}

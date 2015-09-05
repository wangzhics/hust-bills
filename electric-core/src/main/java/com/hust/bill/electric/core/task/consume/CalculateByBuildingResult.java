package com.hust.bill.electric.core.task.consume;

import com.hust.bill.electric.bean.task.consume.ConsumeTaskResultBean;

public class CalculateByBuildingResult {

	private ConsumeTaskResultBean resultBean;

	public CalculateByBuildingResult(ConsumeTaskResultBean resultBean) {
		this.resultBean = resultBean;
	}
	
	public ConsumeTaskResultBean getResultBean() {
		return resultBean;
	}
}

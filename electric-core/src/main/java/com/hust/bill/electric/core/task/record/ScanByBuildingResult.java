package com.hust.bill.electric.core.task.record;

import com.hust.bill.electric.bean.task.record.RecordTaskResultBean;

public class ScanByBuildingResult {

	private RecordTaskResultBean resultBean;
	
	public ScanByBuildingResult(RecordTaskResultBean resultBean) {
		this.resultBean = resultBean;
	}

	public RecordTaskResultBean getResultBean() {
		return resultBean;
	}
}

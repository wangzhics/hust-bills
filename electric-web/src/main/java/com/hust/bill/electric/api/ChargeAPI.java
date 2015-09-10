package com.hust.bill.electric.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.bill.electric.bean.ChargeRecord;
import com.hust.bill.electric.service.IRecordService;

@Controller
@RequestMapping(value="/api/charge")
public class ChargeAPI {
	
	@Autowired 
	private IRecordService recordService;

	@RequestMapping(value="/{buildingName}/{roomName}/{limit}/{offset}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getRemains(@PathVariable String buildingName, @PathVariable String roomName, @PathVariable int limit, @PathVariable int offset) {
		Map<String, Object> resultMap = new HashMap<String, Object>(2);
		int total = recordService.getChargeCount(buildingName, roomName);
		ChargeRecord[] rows = recordService.getCharges(buildingName, roomName, limit, offset);
		resultMap.put("total", total);
		resultMap.put("rows", rows);
		return resultMap;
	}
}

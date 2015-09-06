package com.hust.bill.electric.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.service.IBuildingService;

@Controller
@RequestMapping(value="/api/building")
public class BuildingAPI {
	
	@Autowired
	private IBuildingService buildingService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<String>> getAll() {
		Map<String, List<String>> areaBuildingMap = new HashMap<String, List<String>>(5);
		Building[] buildings = buildingService.getAll();
		for(Building building : buildings) {
			String area = building.getArea();
			List<String> areaBuildingList = areaBuildingMap.get(area);
			if(areaBuildingList == null) {
				areaBuildingList = new ArrayList<String>(30);
				areaBuildingMap.put(area, areaBuildingList);
			}
			areaBuildingList.add(building.getName());
		}
		return areaBuildingMap;
	}

}

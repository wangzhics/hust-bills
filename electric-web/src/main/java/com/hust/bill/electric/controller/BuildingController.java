package com.hust.bill.electric.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRecordService;

@Controller
public class BuildingController {

	@Autowired 
	private IBuildingService buildingService;
	
	@Autowired 
	private IRecordService recordService;
	
	@RequestMapping(value = "/{buildingName}", method = RequestMethod.GET)
	public String get(@PathVariable String buildingName, Model model, HttpServletResponse response) {
		Building building = buildingService.getByName(buildingName);
		if(building == null) {
			 return "redirect:/";
		}
		Map<String, RemainRecord> lastRemainMap = recordService.getLastRemainsByBuilding(buildingName);
		Map<String, List<RemainRecord>> floorMap = new HashMap<String, List<RemainRecord>>(building.getFloor());
		Iterator<Entry<String, RemainRecord>> lastIterator = lastRemainMap.entrySet().iterator();
		while (lastIterator.hasNext()) {
			Entry<String, RemainRecord> entry = lastIterator.next();
			String roomName = entry.getKey();
			String roomFloor = roomName.substring(0, 1);
			List<RemainRecord> floorList = floorMap.get(roomFloor);
			if(floorList == null) {
				floorList = new ArrayList<RemainRecord>(30);
				floorMap.put(roomFloor, floorList);
			}
			floorList.add(entry.getValue());
		}
		model.addAttribute("floorMap", floorMap);
		model.addAttribute("building", building);
		return "building";
	}
}

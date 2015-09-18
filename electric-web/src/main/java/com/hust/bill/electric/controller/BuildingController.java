package com.hust.bill.electric.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IRoomService;

@Controller
public class BuildingController {

	@Autowired 
	private IBuildingService buildingService;
	
	@Autowired
	private IRoomService roomService;
	
	@RequestMapping(value = "/{buildingName}", method = RequestMethod.GET)
	public String get(@PathVariable String buildingName, Model model, HttpServletResponse response) {
		Building building = buildingService.getByName(buildingName);
		if(building == null) {
			 return "redirect:/";
		}
		Room[] rooms = roomService.getByBuilding(building.getName());
		
		model.addAttribute("building", building);
		return "building";
	}
}

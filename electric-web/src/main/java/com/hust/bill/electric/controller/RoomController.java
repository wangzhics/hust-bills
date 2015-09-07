package com.hust.bill.electric.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.service.IRoomService;

@Controller
public class RoomController {

	@Autowired 
	private IRoomService roomService;
	
	@RequestMapping(value = "/{buildingName}/{roomName}", method = RequestMethod.GET)
	public String get(@PathVariable String buildingName, @PathVariable String roomName, Model model, RedirectAttributes attrs) {
		if(StringUtils.isEmpty(buildingName)){
			return "redirect:/";
		}
		if(StringUtils.isEmpty(roomName)){
			return "redirect:/" + buildingName;
		}
		Room room = roomService.getByNames(buildingName, roomName);
		if(room == null) {
			attrs.addFlashAttribute("roomFalse", true);
			return "redirect:/" + buildingName;
		}
		model.addAttribute("room", room);
		return "room";
	}
}

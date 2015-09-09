package com.hust.bill.electric.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.service.IRoomService;

@Controller
public class RoomController {

	@Autowired 
	private IRoomService roomService;
	
	@RequestMapping(value = "/{buildingName}/{roomName}", method = RequestMethod.GET)
	public String get(@PathVariable String buildingName, @PathVariable String roomName, Model model, HttpServletResponse response) {
		Room room = roomService.getByNames(buildingName, roomName);
		if(room == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		model.addAttribute("room", room);
		return "room";
	}
}

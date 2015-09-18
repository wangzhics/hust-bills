package com.hust.bill.electric.controller;

import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hust.bill.electric.bean.RemainRecord;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.query.RoomRank;
import com.hust.bill.electric.service.IConsumeService;
import com.hust.bill.electric.service.IRecordService;
import com.hust.bill.electric.service.IRoomService;

@Controller
public class RoomController {

	@Autowired 
	private IRoomService roomService;
	
	@Autowired 
	private IRecordService recordService;
	
	@Autowired 
	private IConsumeService consumeService;
	
	@RequestMapping(value = "/{buildingName}/{roomName}", method = RequestMethod.GET)
	public String get(@PathVariable String buildingName, @PathVariable String roomName, Model model, HttpServletResponse response) {
		Room room = roomService.getByNames(buildingName, roomName);
		if(room == null) {
			return "redirect:/";
		}
		model.addAttribute("room", room);
		
		RemainRecord lastRemain = recordService.getLastRemain(buildingName, roomName);
		model.addAttribute("lastRemain", lastRemain);
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DATE, -1);
		Calendar week_ago = Calendar.getInstance();
		week_ago.add(Calendar.DATE, -7);
		RoomRank roomRank = consumeService.getRoomRank(buildingName, roomName, week_ago.getTime(), yesterday.getTime());
		model.addAttribute("roomRank", roomRank);
		
		if(roomRank.getAverage() > 0) {
			int dayCount = (int) (lastRemain.getRemain() / roomRank.getAverage());
			model.addAttribute("dayCount", dayCount);
		} else {
			model.addAttribute("dayCount", "--");
		}
		
		return "room";
	}
}

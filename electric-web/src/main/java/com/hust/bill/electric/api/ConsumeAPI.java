package com.hust.bill.electric.api;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hust.bill.electric.bean.Building;
import com.hust.bill.electric.bean.Consume;
import com.hust.bill.electric.bean.Room;
import com.hust.bill.electric.bean.query.BuildingDateAverage;
import com.hust.bill.electric.service.IBuildingService;
import com.hust.bill.electric.service.IConsumeService;
import com.hust.bill.electric.service.IRoomService;

@Controller
@RequestMapping(value="/api/consume")
public class ConsumeAPI {
	
	@Autowired 
	private IRoomService roomService;
	
	@Autowired 
	private IBuildingService buildingService;
	
	@Autowired 
	private IConsumeService consumeService;

	@RequestMapping(value="/{buildingName}/week/avg", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BuildingDateAverage[]> getBuildingWeekAverage(@PathVariable String buildingName) {
		if(StringUtils.isEmpty(buildingName)){
			return new ResponseEntity<BuildingDateAverage[]>(HttpStatus.BAD_REQUEST);
		}
		Building building = buildingService.getByName(buildingName);
		if(building == null) {
			return new ResponseEntity<BuildingDateAverage[]>(HttpStatus.NOT_ACCEPTABLE);
		}
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DATE, -1);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -7);
		BuildingDateAverage[] averages =  consumeService.getBuildingDateAvg(buildingName, startCalendar.getTime(), endCalendar.getTime());
		return new ResponseEntity<BuildingDateAverage[]>(averages, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{buildingName}/{roomName}/week", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Consume[]> getRoomWeek(@PathVariable String buildingName, @PathVariable String roomName) {
		if(StringUtils.isEmpty(buildingName) || StringUtils.isEmpty(roomName)){
			return new ResponseEntity<Consume[]>(HttpStatus.BAD_REQUEST);
		}
		Room room = roomService.getByNames(buildingName, roomName);
		if(room == null) {
			return new ResponseEntity<Consume[]>(HttpStatus.NOT_ACCEPTABLE);
		}
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DATE, -1);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -7);
		Consume[] consumes =  consumeService.getConsumesByRoom(buildingName, roomName, startCalendar.getTime(), endCalendar.getTime());
		return new ResponseEntity<Consume[]>(consumes, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{buildingName}/month/avg", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<BuildingDateAverage[]> getBuildingMonthAverage(@PathVariable String buildingName) {
		if(StringUtils.isEmpty(buildingName)){
			return new ResponseEntity<BuildingDateAverage[]>(HttpStatus.BAD_REQUEST);
		}
		Building building = buildingService.getByName(buildingName);
		if(building == null) {
			return new ResponseEntity<BuildingDateAverage[]>(HttpStatus.NOT_ACCEPTABLE);
		}
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DATE, -1);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -28);
		BuildingDateAverage[] averages =  consumeService.getBuildingDateAvg(buildingName, startCalendar.getTime(), endCalendar.getTime());
		return new ResponseEntity<BuildingDateAverage[]>(averages, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{buildingName}/{roomName}/month", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Consume[]> getRoomMonth(@PathVariable String buildingName, @PathVariable String roomName, Model model) {
		if(StringUtils.isEmpty(buildingName) || StringUtils.isEmpty(roomName)){
			return new ResponseEntity<Consume[]>(HttpStatus.BAD_REQUEST);
		}
		Room room = roomService.getByNames(buildingName, roomName);
		if(room == null) {
			return new ResponseEntity<Consume[]>(HttpStatus.NOT_ACCEPTABLE);
		}
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.DATE, -1);
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -28);
		Consume[] consumes =  consumeService.getConsumesByRoom(buildingName, roomName, startCalendar.getTime(), endCalendar.getTime());
		return new ResponseEntity<Consume[]>(consumes, HttpStatus.OK);
	}
	
	
}

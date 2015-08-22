package com.hust.bill.electric.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hust.bill.electric.dao.IRoomTempDAO;
import com.hust.bill.electric.service.IRoomService;

@Service(value="roomService")
public class RoomService implements IRoomService {

	@Autowired
	private IRoomTempDAO roomTempDAO;
	
	@Transactional
	public void extractTemp() {
		roomTempDAO.truncate();
		roomTempDAO.generate();
	}

	public void clearTemp() {
		roomTempDAO.truncate();
	}

}

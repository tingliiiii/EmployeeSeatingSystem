package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.SeatDao;
import com.example.demo.model.Seat;

@Service
public class SeatService {
	
	@Autowired
	private SeatDao seatingDao;

	public List<Seat> findAllSeats() {
		return seatingDao.findAllSeats();
	}
	

}

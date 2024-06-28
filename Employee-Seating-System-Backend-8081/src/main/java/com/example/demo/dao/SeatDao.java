package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.po.Seat;

public interface SeatDao {

	int addSeat(Seat seat);
	int updateSeat(Integer floorSeatSeq, Integer empId);
	int clearSeat(Integer floorSeatSeq);
	List<Seat> findAllSeats();
}

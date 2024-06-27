package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.Seat;

public interface SeatDao {

	List<Seat> findAllSeats();
}

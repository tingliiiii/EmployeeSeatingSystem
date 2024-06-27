package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Seat;

@Repository
public class SeatDaoImpl implements SeatDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Seat> findAllSeats() {
		String sql = "SELECT floor_seat_seq, floor_no, seat_no FROM seating_chart ORDER BY floor_no";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Seat.class));
	}

	
}

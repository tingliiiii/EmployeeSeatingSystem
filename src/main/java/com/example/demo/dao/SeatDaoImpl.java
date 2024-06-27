package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.po.Seat;

@Repository
public class SeatDaoImpl implements SeatDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addSeat(Seat seat) {
		String sql = "INSERT INTO seating_chart (floor_no, seat_no) VALUES (?, ?)";
		return jdbcTemplate.update(sql, seat.getFloorNo(), seat.getSeatNo());
	}

	@Override
	public int updateSeat(Integer floorSeatSeq, Integer empId) {
		String sql = "UPDATE seating_chart SET emp_id = ? WHERE floor_seat_seq = ?";
		return jdbcTemplate.update(sql, empId, floorSeatSeq);
	}

	@Override
	public int clearSeat(Integer floorSeatSeq) {
		String sql = "UPDATE seating_chart SET emp_id = null WHERE floor_seat_seq = ?";
		// System.out.println("UPDATE seating_chart SET emp_id = null WHERE floor_seat_seq = " + floorSeatSeq);
		return jdbcTemplate.update(sql, floorSeatSeq);
	}

	@Override
	public List<Seat> findAllSeats() {
		String sql = "SELECT floor_seat_seq, floor_no, seat_no, emp_id FROM seating_chart ORDER BY floor_no";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Seat.class));
	}

}

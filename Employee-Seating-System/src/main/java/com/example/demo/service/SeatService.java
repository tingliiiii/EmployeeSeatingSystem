package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.dao.SeatDao;
import com.example.demo.model.po.Employee;
import com.example.demo.model.po.Seat;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SeatService {

	@Autowired
	private SeatDao seatingDao;

	@Autowired
	private EmployeeDao employeeDao;

	public boolean addSeat(Seat seat) {
		return seatingDao.addSeat(seat) > 0;
	}

	@Transactional
	public boolean updateSeat(Integer floorSeatSeq, Integer empId) {
		try {
			Employee employee = employeeDao.getEmployee(empId);
			boolean state1 = false;
			if (employee.getFloorSeatSeq() == null) {
				state1 = true;
			} else {
				state1 = seatingDao.clearSeat(employee.getFloorSeatSeq()) > 0;
			}
			boolean state2 = seatingDao.updateSeat(floorSeatSeq, empId) > 0;
			boolean state3 = employeeDao.updateEmployee(empId, floorSeatSeq) > 0;

			if (state1 && state2 && state3) {
				return true;
			} else {
				throw new RuntimeException("Failed to update seat and employee data");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return false;
		}
	}

	public List<Seat> findAllSeats() {
		return seatingDao.findAllSeats();
	}

}

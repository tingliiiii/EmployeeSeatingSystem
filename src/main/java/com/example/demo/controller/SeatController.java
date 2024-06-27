package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.Seat;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.StatusMessage;
import com.example.demo.service.SeatService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/seats")
public class SeatController {

	@Autowired
	private SeatService seatService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Seat>>> findAllSeats() {
		try {
			List<Seat> seats = seatService.findAllSeats();
			ApiResponse apiResponse = new ApiResponse<>(true, StatusMessage.查詢成功.name(), seats);
			return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			ApiResponse apiResponse = new ApiResponse<>(false, e.getMessage(), null);
			return ResponseEntity.ok(apiResponse);
		}

	}

}

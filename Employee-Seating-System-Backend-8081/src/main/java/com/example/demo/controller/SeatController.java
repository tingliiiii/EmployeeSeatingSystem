package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.UpdateSeat;
import com.example.demo.model.po.Seat;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.StatusMessage;
import com.example.demo.service.SeatService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/seats")
public class SeatController {

	@Autowired
	private SeatService seatService;

	@GetMapping
	@Operation(summary = "取得所有座位")
	public ResponseEntity<ApiResponse<List<Seat>>> findAllSeats() {
		try {
			List<Seat> seats = seatService.findAllSeats();
			return ResponseEntity.ok(new ApiResponse<>(true, StatusMessage.查詢成功.name(), seats));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(false, e.getMessage(), null));
		}
	}
	
	@PostMapping
	@Operation(summary = "新增座位")
	public ResponseEntity<ApiResponse<Void>> createSeat(@RequestBody Seat seat) {
		try {
			Boolean state = seatService.addSeat(seat);
			if (state) {
				return ResponseEntity.ok(new ApiResponse<>(state, StatusMessage.新增成功.name(), null));
			}
			return ResponseEntity.ok(new ApiResponse<>(state, StatusMessage.新增失敗.name(), null));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(false, e.getMessage(), null));
		}
	}

	@PutMapping
	@Operation(summary = "調整員工座位")
	public ResponseEntity<ApiResponse<Void>> updateSeat(@RequestBody UpdateSeat updateSeat) {
		try {
			log.info("updateSeat: " + updateSeat);
			Boolean state = seatService.updateSeat(updateSeat.getFloorSeatSeq(), updateSeat.getEmpId());
			if (state) {
				return ResponseEntity.ok(new ApiResponse<>(state, StatusMessage.修改成功.name(), null));
			}
			return ResponseEntity.ok(new ApiResponse<>(state, StatusMessage.修改失敗.name(), null));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(false, e.getMessage(), null));
		}

	}

}

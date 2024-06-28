package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.UpdateSeat;
import com.example.demo.model.po.Employee;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.StatusMessage;
import com.example.demo.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	@Operation(summary = "取得所有員工")
	public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
		try {
			List<Employee> employees = employeeService.getAllEmployees();
			return ResponseEntity.ok(new ApiResponse<>(true, StatusMessage.查詢成功.name(), employees));
		} catch (Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>(false, e.getMessage(), null));
		}
	}

	@PostMapping
	@Operation(summary = "新增員工")
	public ResponseEntity<ApiResponse<Void>> createEmployee(@RequestBody Employee employee) {
		try {
			Boolean state = employeeService.addEmployee(employee);
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

}

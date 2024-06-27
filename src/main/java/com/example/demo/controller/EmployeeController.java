package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.model.response.ApiResponse;
import com.example.demo.model.response.StatusMessage;
import com.example.demo.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees() {
		try {
			List<Employee> employees = employeeService.getAllEmployees();
			ApiResponse apiResponse = new ApiResponse<>(true, StatusMessage.查詢成功.name(), employees);
			return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			log.error(e.getMessage());
			ApiResponse apiResponse = new ApiResponse<>(false, e.getMessage(), null);
			return ResponseEntity.ok(apiResponse);
		}
	}

}

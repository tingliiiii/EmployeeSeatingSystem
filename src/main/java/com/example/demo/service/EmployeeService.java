package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.model.Employee;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeDao employeeDao;

	public int addEmployee(Employee employee) {
		return employeeDao.addEmployee(employee);
	}

	public int deleteEmployee(Integer empId) {
		return employeeDao.deleteEmployee(empId);
	}

	public int updateEmployee(Integer empId, Integer floorSeatSeq) {
		return employeeDao.updateEmployee(empId, floorSeatSeq);
	}

	public Employee getEmployee(Integer empId) {
		return employeeDao.getEmployee(empId);
	}

	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

}

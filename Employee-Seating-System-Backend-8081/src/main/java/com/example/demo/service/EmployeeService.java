package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.EmployeeDao;
import com.example.demo.model.po.Employee;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeDao employeeDao;

	public boolean addEmployee(Employee employee) {
		return employeeDao.addEmployee(employee) > 0;
	}

	public boolean deleteEmployee(Integer empId) {
		return employeeDao.deleteEmployee(empId) > 0;
	}

	public Employee getEmployee(Integer empId) {
		return employeeDao.getEmployee(empId);
	}

	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

}

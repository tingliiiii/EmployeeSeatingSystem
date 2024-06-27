package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.po.Employee;

public interface EmployeeDao {

	int addEmployee(Employee employee);
	int deleteEmployee(Integer empId);
	int updateEmployee(Integer empId, Integer floorSeatSeq);
	Employee getEmployee(Integer empId);
	List<Employee> getAllEmployees();
	
}

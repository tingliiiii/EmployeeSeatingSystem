package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addEmployee(Employee employee) {
		String sql = "INSERT INTO employees(emp_id, name, email, floor_seat_seq) VALUES (?, ?, ?, ?)";
		return jdbcTemplate.update(sql, employee.getEmpId(), employee.getName(), employee.getEmail(),
				employee.getFloorSeatSeq());
	}

	@Override
	public int deleteEmployee(Integer empId) {
		String sql = "DELETE FROM employees WHERE emp_id=?";
		return jdbcTemplate.update(sql, empId);
	}

	@Override
	public int updateEmployee(Integer empId, Integer floorSeatSeq) {
		String sql = "UPDATE employees SET floor_seat_seq=? WHERE emp_id=?";
		return jdbcTemplate.update(sql, floorSeatSeq, empId);
	}
	
	@Override
	public Employee getEmployee(Integer empId) {
		String sql = "SELECT emp_id, name, email, floor_seat_seq FROM employees WHERE emp_id=?";
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Employee.class), empId);
	}

	@Override
	public List<Employee> getAllEmployees() {
		String sql = "SELECT emp_id, name, email, floor_seat_seq FROM employees";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Employee.class));
	}


}

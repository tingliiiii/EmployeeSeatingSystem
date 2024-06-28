package com.example.demo.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
 
	private Integer empId;
	private String name;
	private String email;
	private Integer floorSeatSeq;
}

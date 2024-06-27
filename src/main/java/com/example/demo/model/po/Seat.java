package com.example.demo.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

	private Integer floorSeatSeq;
	private Integer floorNo;
	private Integer seatNo;
	private Integer empId;
}

package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seat {

	private Integer floorSeatSql;
	private Integer floorNo;
	private Integer seatNo;
}

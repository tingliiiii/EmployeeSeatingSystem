CREATE DATABASE employees;

CREATE TABLE seating_chart (
  floor_seat_seq INT PRIMARY KEY AUTO_INCREMENT,
  floor_no INT,
  seat_no INT
);

CREATE TABLE employees (
  emp_id CHAR(5) PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  floor_seat_seq INT,
  FOREIGN KEY (floor_seat_seq) REFERENCES seating_chart(floor_seat_seq)
);

INSERT INTO seating_chart (floor_no, seat_no)
VALUES (1, 1), (1, 2), (1, 3), (1, 4),
       (2, 1), (2, 2), (2, 3), (2, 4),
       (3, 1), (3, 2), (3, 3), (3, 4),
       (4, 1), (4, 2), (4, 3), (4, 4);

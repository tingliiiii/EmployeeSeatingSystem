CREATE DATABASE IF NOT EXISTS office;

DROP TABLE seating_chart;
DROP TABLE employees;
CREATE TABLE IF NOT EXISTS seating_chart (
  floor_seat_seq INT AUTO_INCREMENT PRIMARY KEY,
  floor_no INT,
  seat_no INT,
  emp_id CHAR(5)
);

ALTER TABLE seating_chart ADD CONSTRAINT FOREIGN KEY (emp_id) REFERENCES employees(emp_id) ON DELETE SET NULL;

CREATE TABLE IF NOT EXISTS employees (
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

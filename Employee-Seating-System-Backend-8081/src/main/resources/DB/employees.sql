-- 新增 DATABASE
CREATE DATABASE IF NOT EXISTS office;
USE office;

-- 刪除 TABLE（測試用）
-- ALTER TABLE seating_chart DROP FOREIGN KEY fk_emp_id;
-- DROP TABLE IF EXISTS employees;
-- DROP TABLE IF EXISTS seating_chart;

-- 新增座位表
CREATE TABLE IF NOT EXISTS seating_chart (
  floor_seat_seq INT AUTO_INCREMENT PRIMARY KEY,
  floor_no INT,
  seat_no INT,
  emp_id CHAR(5)
);

-- 新增員工
CREATE TABLE IF NOT EXISTS employees (
  emp_id CHAR(5) PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100),
  floor_seat_seq INT,
  CONSTRAINT fk_floor_seat_seq FOREIGN KEY (floor_seat_seq) 
  REFERENCES seating_chart(floor_seat_seq) ON DELETE SET NULL
);

-- 座位表的外鍵約束需在 employees 存在後才可新增
ALTER TABLE seating_chart ADD CONSTRAINT fk_emp_id 
FOREIGN KEY (emp_id) REFERENCES employees(emp_id) ON DELETE SET NULL;

-- 新增座位預設資料
INSERT INTO seating_chart (floor_no, seat_no)
VALUES (1, 1), (1, 2), (1, 3), (1, 4),
       (2, 1), (2, 2), (2, 3), (2, 4),
       (3, 1), (3, 2), (3, 3), (3, 4),
       (4, 1), (4, 2), (4, 3), (4, 4);

-- 新增員工預設資料
INSERT INTO employees(emp_id, name, email) 
VALUES ('12006','Rose','rose@gmail.com'), 
	   ('13040','Mary','mary@gmail.com'), 
	   ('16142','Jack','jack@gmail.com'),
	   ('17081','John','john@gmail.com');
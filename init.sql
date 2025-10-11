CREATE DATABASE IF NOT EXISTS ibanking-user;
USE ibanking-user;
CREATE TABLE users(
  id INT AUTO_INCREMENT PRIMARY KEY,
  name NVARCHAR(100) NOT NULL,
  password VARCHAR(20) NOT NULL,
  birth_date DATE,
  gender VARCHAR(5),
  student_id VARCHAR(10) NOT NULL UNIQUE,
  classes VARCHAR(10) NOT NULL
);

USE ibanking-user
INSERT INTO users (name, password, birth_date, gender, student_id, classes) VALUES
('Nguyen Van A', '123456', '2001-03-15', 'M', 'SV001', 'T01'),
('Tran Thi B', 'abcxyz', '2002-07-22', 'F', 'SV002', 'T01'),
('Le Van C', 'mypassword', '2000-12-01', 'M', 'SV003', 'T01'),
('Pham Thi D', 'qwerty', '2003-05-09', 'F', 'SV004', 'T01'),
('Hoang Van E', 'pass123', '2001-11-30', 'M', 'SV005', 'T01');


CREATE DATABASE IF NOT EXISTS ibanking-tuition;
USE ibanking-tuition;
CREATE TABLE tuitions(
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  amount DOUBLE NOT NULL,
  date DATETIME,
  isPaid BOOLEAN DEFAULT FALSE
);

USE ibanking-tuition;
INSERT INTO tuitions (user_id, amount, date, is_paid) VALUES
(1, 1500.00, '2025-10-01 10:00:00', FALSE),
(2, 1600.50, '2025-10-02 11:30:00', TRUE),
(3, 1550.75, '2025-10-03 09:45:00', FALSE),
(4, 1700.00, '2025-10-04 14:20:00', TRUE),
(5, 1650.25, '2025-10-05 16:10:00', FALSE),
(1, 1680.25, '2025-10-05 16:10:00', FALSE);

CREATE DATABASE IF NOT EXISTS ibanking-transaction;
USE ibanking-transaction;
CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    tuition_id INT NOT NULL,
    student_id VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    amount DOUBLE NOT NULL,
    type VARCHAR(50) NOT NULL,       -- e.g. "PAYMENT", "REFUND"
    status VARCHAR(20) NOT NULL,     -- "PENDING", "SUCCESS", "FAILED"
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

INSERT INTO transactions (user_id, tuition_id, student_id, date, amount, type, status, created_at, updated_at) VALUES
(1, 101, 'SV001', '2025-10-01', 1500.00, 'PAYMENT', 'SUCCESS', '2025-10-01 10:00:00', '2025-10-01 10:00:00'),
(1, 102, 'SV001', '2025-10-03', 1500.00, 'REFUND', 'SUCCESS', '2025-10-03 08:30:00', '2025-10-03 08:30:00'),
(2, 103, 'SV002', '2025-10-02', 1600.50, 'PAYMENT', 'SUCCESS', '2025-10-02 11:30:00', '2025-10-02 11:30:00'),
(2, 104, 'SV002', '2025-10-05', 1600.50, 'REFUND', 'FAILED', '2025-10-05 09:15:00', '2025-10-05 09:15:00'),
(3, 105, 'SV003', '2025-10-03', 1550.75, 'PAYMENT', 'PENDING', '2025-10-03 09:45:00', '2025-10-03 09:45:00'),
(3, 106, 'SV003', '2025-10-06', 1550.75, 'REFUND', 'SUCCESS', '2025-10-06 13:10:00', '2025-10-06 13:10:00'),
(4, 107, 'SV004', '2025-10-04', 1700.00, 'PAYMENT', 'SUCCESS', '2025-10-04 14:20:00', '2025-10-04 14:20:00'),
(4, 108, 'SV004', '2025-10-06', 1700.00, 'REFUND', 'FAILED', '2025-10-06 15:40:00', '2025-10-06 15:40:00'),
(5, 109, 'SV005', '2025-10-05', 1650.25, 'PAYMENT', 'PENDING', '2025-10-05 16:10:00', '2025-10-05 16:10:00'),
(5, 110, 'SV005', '2025-10-07', 1650.25, 'REFUND', 'SUCCESS', '2025-10-07 09:25:00', '2025-10-07 09:25:00');

CREATE DATABASE IF NOT EXISTS ibanking-payment;
USE ibanking-payment;
CREATE TABLE payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    tuition_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    method VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    transaction_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL
);

CREATE DATABASE IF NOT EXISTS ibanking-payment;
USE ibanking-payment;
CREATE TABLE IF NOT EXISTS payment_accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    balance DOUBLE NOT NULL DEFAULT 0.0
);

INSERT INTO payments (user_id, tuition_id, amount, method, status, transaction_id, created_at, updated_at) VALUES
(1, 101, 1500.00, 'CREDIT_CARD', 'SUCCESS', 1, '2025-10-01 10:00:00', '2025-10-01 10:00:00'),
(1, 102, 1500.00, 'BANK_TRANSFER', 'SUCCESS', 2, '2025-10-03 08:30:00', '2025-10-03 08:30:00'),
(2, 103, 1600.50, 'CASH', 'SUCCESS', 3, '2025-10-02 11:30:00', '2025-10-02 11:30:00'),
(2, 104, 1600.50, 'BANK_TRANSFER', 'FAILED', 4, '2025-10-05 09:15:00', '2025-10-05 09:15:00'),
(3, 105, 1550.75, 'CREDIT_CARD', 'PENDING', 5, '2025-10-03 09:45:00', '2025-10-03 09:45:00'),
(3, 106, 1550.75, 'BANK_TRANSFER', 'SUCCESS', 6, '2025-10-06 13:10:00', '2025-10-06 13:10:00'),
(4, 107, 1700.00, 'CASH', 'SUCCESS', 7, '2025-10-04 14:20:00', '2025-10-04 14:20:00'),
(4, 108, 1700.00, 'CREDIT_CARD', 'FAILED', 8, '2025-10-06 15:40:00', '2025-10-06 15:40:00'),
(5, 109, 1650.25, 'BANK_TRANSFER', 'PENDING', 9, '2025-10-05 16:10:00', '2025-10-05 16:10:00'),
(5, 110, 1650.25, 'CASH', 'SUCCESS', 10, '2025-10-07 09:25:00', '2025-10-07 09:25:00');

INSERT INTO payment_accounts (user_id, balance) VALUES
(1, 2500.00),
(2, 3200.50),
(3, 1500.75),
(4, 4200.00),
(5, 2850.25);


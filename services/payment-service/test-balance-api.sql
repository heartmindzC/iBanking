-- Script để tạo dữ liệu test cho API getBalanceByUserId
-- Chạy script này trong MySQL database: ibanking-payment

-- Tạo bảng payment_accounts nếu chưa có
CREATE TABLE IF NOT EXISTS payment_accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,
    balance DOUBLE NOT NULL DEFAULT 0.0
);

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM payment_accounts WHERE user_id IN (1, 2, 3, 100, 200);

-- Thêm dữ liệu test
INSERT INTO payment_accounts (user_id, balance) VALUES 
(1, 1500.50),
(2, 2500.75),
(3, 0.0),
(100, 10000.00),
(200, 500.25);

-- Kiểm tra dữ liệu đã được thêm
SELECT * FROM payment_accounts ORDER BY user_id;

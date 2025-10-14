# Email Notification Service

Dịch vụ gửi email thông báo cho hệ thống iBanking, hỗ trợ gửi email OTP và thông báo giao dịch thành công.

## Tính năng

- ✅ Gửi email OTP từ OTP service
- ✅ Gửi email thông báo giao dịch thành công từ Payment service
- ✅ Lưu trữ lịch sử notification vào database
- ✅ REST API endpoints để các service khác gọi
- ✅ Template email đẹp mắt với HTML/CSS

## Cấu hình

### 1. Database
Tạo database MySQL với tên `ibanking_notifications`:
```sql
CREATE DATABASE ibanking_notifications;
```

### 2. Email Configuration
Cập nhật file `application.properties`:
```properties
# Thay đổi thông tin email của bạn
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
```

**Lưu ý:** Để sử dụng Gmail, bạn cần:
1. Bật 2-Factor Authentication
2. Tạo App Password thay vì sử dụng mật khẩu thường

### 3. Database Configuration
Cập nhật thông tin database trong `application.properties`:
```properties
spring.datasource.username=your-db-username
spring.datasource.password=your-db-password
```

## API Endpoints

### 1. Gửi Email OTP
**POST** `/api/notifications/send-otp`

Request Body:
```json
{
    "toEmail": "user@example.com",
    "otpCode": "123456"
}
```

Response:
```json
{
    "success": true,
    "message": "OTP email sent successfully",
    "timestamp": "2025-01-27T10:30:00"
}
```

### 2. Gửi Email Thông Báo Giao Dịch Thành Công
**POST** `/api/notifications/send-transaction-success`

Request Body:
```json
{
    "toEmail": "user@example.com",
    "transactionId": "TXN123456789",
    "amount": "1000000",
    "recipientName": "Nguyễn Văn A",
    "transactionType": "Chuyển khoản"
}
```

Response:
```json
{
    "success": true,
    "message": "Transaction success email sent successfully",
    "timestamp": "2025-01-27T10:30:00"
}
```

### 3. Health Check
**GET** `/api/notifications/health`

Response:
```json
{
    "success": true,
    "message": "Notification service is running",
    "timestamp": "2025-01-27T10:30:00"
}
```

## Cách sử dụng từ các service khác

### Từ OTP Service:
```java
@Autowired
private RestTemplate restTemplate;

public void sendOtpEmail(String email, String otpCode) {
    String url = "http://localhost:8083/api/notifications/send-otp";
    
    OtpEmailRequest request = new OtpEmailRequest(email, otpCode);
    
    ResponseEntity<NotificationResponse> response = restTemplate.postForEntity(
        url, request, NotificationResponse.class);
    
    if (response.getBody().isSuccess()) {
        System.out.println("OTP email sent successfully");
    }
}
```

### Từ Payment Service:
```java
@Autowired
private RestTemplate restTemplate;

public void sendTransactionSuccessNotification(String email, String transactionId, 
                                             String amount, String recipientName, 
                                             String transactionType) {
    String url = "http://localhost:8083/api/notifications/send-transaction-success";
    
    TransactionSuccessRequest request = new TransactionSuccessRequest(
        email, transactionId, amount, recipientName, transactionType);
    
    ResponseEntity<NotificationResponse> response = restTemplate.postForEntity(
        url, request, NotificationResponse.class);
    
    if (response.getBody().isSuccess()) {
        System.out.println("Transaction success email sent successfully");
    }
}
```

## Chạy ứng dụng

1. **Cài đặt dependencies:**
```bash
mvn clean install
```

2. **Chạy ứng dụng:**
```bash
mvn spring-boot:run
```

3. **Kiểm tra service:**
```bash
curl http://localhost:8083/api/notifications/health
```

## Database Schema

Bảng `notifications` sẽ được tự động tạo với các trường:
- `id`: Primary key
- `to_email`: Email người nhận
- `notification_type`: Loại notification (OTP_EMAIL, TRANSACTION_SUCCESS)
- `subject`: Tiêu đề email
- `content`: Nội dung email (HTML)
- `status`: Trạng thái (PENDING, SENT, FAILED)
- `transaction_id`: Mã giao dịch (nếu có)
- `otp_code`: Mã OTP (nếu có)
- `amount`: Số tiền (nếu có)
- `recipient_name`: Tên người nhận (nếu có)
- `transaction_type`: Loại giao dịch (nếu có)
- `created_at`: Thời gian tạo
- `sent_at`: Thời gian gửi
- `error_message`: Thông báo lỗi (nếu có)

## Troubleshooting

### Lỗi kết nối email:
1. Kiểm tra username/password email
2. Đảm bảo đã bật 2FA và sử dụng App Password
3. Kiểm tra firewall/network

### Lỗi kết nối database:
1. Kiểm tra MySQL đang chạy
2. Kiểm tra thông tin database trong application.properties
3. Đảm bảo database `ibanking_notifications` đã được tạo

### Lỗi 500 Internal Server Error:
1. Kiểm tra logs trong console
2. Đảm bảo tất cả dependencies đã được cài đặt
3. Kiểm tra cấu hình email và database


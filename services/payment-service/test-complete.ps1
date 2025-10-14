# Script test hoàn chỉnh cho API getBalanceByUserId
Write-Host "=== TESTING PAYMENT SERVICE API ===" -ForegroundColor Green
Write-Host ""

$baseUrl = "http://localhost:8080/iBanking/payments"

# Đợi service khởi động
Write-Host "Đang chờ service khởi động..." -ForegroundColor Yellow
Start-Sleep -Seconds 15

# Tạo dữ liệu test
Write-Host "1. Tạo dữ liệu test..." -ForegroundColor Cyan
$testAccounts = @(
    @{userId=1; balance=1500.50},
    @{userId=2; balance=2500.75},
    @{userId=3; balance=0.0},
    @{userId=100; balance=10000.00}
)

foreach ($account in $testAccounts) {
    $url = "$baseUrl/createTestAccount/$($account.userId)/$($account.balance)"
    try {
        $response = Invoke-RestMethod -Uri $url -Method POST
        Write-Host "✅ Created: $response" -ForegroundColor Green
    } catch {
        Write-Host "❌ Error creating account for user $($account.userId): $($_.Exception.Message)" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "2. Test API getBalanceByUserId..." -ForegroundColor Cyan

# Test các user ID
$testUsers = @(1, 2, 3, 100, 999)  # 999 là user không tồn tại

foreach ($userId in $testUsers) {
    $url = "$baseUrl/getBalanceByUserId/$userId"
    Write-Host "Testing User ID: $userId" -ForegroundColor Yellow
    
    try {
        $response = Invoke-RestMethod -Uri $url -Method GET
        Write-Host "✅ Success: Balance = $response" -ForegroundColor Green
    } catch {
        $statusCode = $_.Exception.Response.StatusCode.value__
        Write-Host "❌ Error ($statusCode): $($_.Exception.Message)" -ForegroundColor Red
    }
    Write-Host "---" -ForegroundColor Gray
}

Write-Host ""
Write-Host "=== TEST COMPLETED ===" -ForegroundColor Green


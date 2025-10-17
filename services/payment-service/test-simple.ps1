# Script đơn giản để test API
Write-Host "Đang chờ service khởi động..." -ForegroundColor Yellow
Start-Sleep -Seconds 10

Write-Host "Testing API getBalanceByUserId..." -ForegroundColor Green

# Test với user ID = 1
$url = "http://localhost:8080/iBanking/payments/getBalanceByUserId/1"
Write-Host "Testing: $url" -ForegroundColor Cyan

try {
    $response = Invoke-RestMethod -Uri $url -Method GET
    Write-Host "✅ Success: Balance = $response" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}





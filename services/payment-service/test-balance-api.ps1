# Script PowerShell để test API getBalanceByUserId
# Đảm bảo service đang chạy trên port 8080

$baseUrl = "http://localhost:8080/iBanking/payments"
$testUsers = @(1, 2, 3, 100, 200, 999)  # 999 là user không tồn tại

Write-Host "=== TESTING API getBalanceByUserId ===" -ForegroundColor Green
Write-Host "Base URL: $baseUrl" -ForegroundColor Yellow
Write-Host ""

foreach ($userId in $testUsers) {
    $url = "$baseUrl/getBalanceByUserId/$userId"
    
    Write-Host "Testing User ID: $userId" -ForegroundColor Cyan
    Write-Host "URL: $url" -ForegroundColor Gray
    
    try {
        $response = Invoke-RestMethod -Uri $url -Method GET -ContentType "application/json"
        Write-Host "✅ Success: Balance = $response" -ForegroundColor Green
    }
    catch {
        $statusCode = $_.Exception.Response.StatusCode.value__
        $errorMessage = $_.Exception.Message
        Write-Host "❌ Error ($statusCode): $errorMessage" -ForegroundColor Red
    }
    
    Write-Host "---" -ForegroundColor Gray
}

Write-Host ""
Write-Host "=== TEST COMPLETED ===" -ForegroundColor Green


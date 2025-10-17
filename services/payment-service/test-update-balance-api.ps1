# Test script for updateBalance API
# Usage: .\test-update-balance-api.ps1

$baseUrl = "http://localhost:8080/payments"

Write-Host "🧪 Testing updateBalance API..." -ForegroundColor Green

# Test 1: Valid update
Write-Host "`n📝 Test 1: Valid balance update" -ForegroundColor Yellow
$userId = 1
$newBalance = 1000.50
$url = "$baseUrl/updateBalance/$userId/$newBalance"

try {
    $response = Invoke-RestMethod -Uri $url -Method PUT -ContentType "application/json"
    Write-Host "✅ Success: $response" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Invalid user ID (negative)
Write-Host "`n📝 Test 2: Invalid user ID (negative)" -ForegroundColor Yellow
$userId = -1
$newBalance = 500.0
$url = "$baseUrl/updateBalance/$userId/$newBalance"

try {
    $response = Invoke-RestMethod -Uri $url -Method PUT -ContentType "application/json"
    Write-Host "✅ Response: $response" -ForegroundColor Green
} catch {
    Write-Host "❌ Expected Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Invalid balance (negative)
Write-Host "`n📝 Test 3: Invalid balance (negative)" -ForegroundColor Yellow
$userId = 1
$newBalance = -100.0
$url = "$baseUrl/updateBalance/$userId/$newBalance"

try {
    $response = Invoke-RestMethod -Uri $url -Method PUT -ContentType "application/json"
    Write-Host "✅ Response: $response" -ForegroundColor Green
} catch {
    Write-Host "❌ Expected Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 4: Non-existent user
Write-Host "`n📝 Test 4: Non-existent user" -ForegroundColor Yellow
$userId = 99999
$newBalance = 1000.0
$url = "$baseUrl/updateBalance/$userId/$newBalance"

try {
    $response = Invoke-RestMethod -Uri $url -Method PUT -ContentType "application/json"
    Write-Host "✅ Response: $response" -ForegroundColor Green
} catch {
    Write-Host "❌ Expected Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 5: Get balance after update
Write-Host "`n📝 Test 5: Get balance after update" -ForegroundColor Yellow
$userId = 1
$url = "$baseUrl/getBalanceByUserId/$userId"

try {
    $response = Invoke-RestMethod -Uri $url -Method GET
    Write-Host "✅ Current balance for user $userId : $response" -ForegroundColor Green
} catch {
    Write-Host "❌ Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n🏁 Testing completed!" -ForegroundColor Green

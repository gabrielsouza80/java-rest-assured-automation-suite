param(
    [string]$TestClass = "PostsTrainingTest"
)

Write-Host "Running test: $TestClass" -ForegroundColor Cyan
mvn -q "-Dtest=$TestClass" test

if ($LASTEXITCODE -ne 0) {
    Write-Host "\nMaven execution failed." -ForegroundColor Red
    exit $LASTEXITCODE
}

$reportPath = Join-Path "target\surefire-reports" ("com.gabriel.tests.{0}.txt" -f $TestClass)
if (-not (Test-Path $reportPath)) {
    $reportPath = Get-ChildItem "target\surefire-reports" -Filter "*.txt" |
    Sort-Object LastWriteTime -Descending |
    Select-Object -First 1 -ExpandProperty FullName
}

if (Test-Path $reportPath) {
    Write-Host "\nReadable summary:" -ForegroundColor Green
    Get-Content $reportPath
}
else {
    Write-Host "\nNo report found in target/surefire-reports." -ForegroundColor Yellow
}

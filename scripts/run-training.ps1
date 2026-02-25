param(
    [string]$TestClass = "PostsTrainingTest"
)

Write-Host "Executando teste: $TestClass" -ForegroundColor Cyan
mvn -q "-Dtest=$TestClass" test

if ($LASTEXITCODE -ne 0) {
    Write-Host "\nFalha na execução do Maven." -ForegroundColor Red
    exit $LASTEXITCODE
}

$reportPath = Join-Path "target\surefire-reports" ("com.gabriel.tests.{0}.txt" -f $TestClass)
if (-not (Test-Path $reportPath)) {
    $reportPath = Get-ChildItem "target\surefire-reports" -Filter "*.txt" |
        Sort-Object LastWriteTime -Descending |
        Select-Object -First 1 -ExpandProperty FullName
}

if (Test-Path $reportPath) {
    Write-Host "\nResumo legível:" -ForegroundColor Green
    Get-Content $reportPath
} else {
    Write-Host "\nNão foi encontrado relatório em target/surefire-reports." -ForegroundColor Yellow
}

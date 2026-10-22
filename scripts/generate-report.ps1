#To View the latest Test Run report, run the below script using the command ".\scripts\generate-report.ps1"
#---------------------------------------------------------------------------------------------=============#

$ErrorActionPreference = "Stop"
$projectRoot = Split-Path -Parent $PSScriptRoot
$resultsDir = Join-Path $projectRoot "target\allure-results"
$reportDir = Join-Path $projectRoot "reports\local"
$allureImage = "andgineer/allure:2.36.0"
$viewerName = "medishop-allure-viewer"

# Validate that Maven has produced Allure results.
$resultFiles = @(
    Get-ChildItem -Path $resultsDir -Filter "*-result.json" -File `
        -ErrorAction SilentlyContinue
)

if ($resultFiles.Count -eq 0) {
    throw "No Allure results found. Run your tests with Maven first."
}

Write-Host "Found $($resultFiles.Count) Allure test results." -ForegroundColor Cyan

# Generate HTML directly into reports/local.
New-Item -ItemType Directory -Force -Path $reportDir | Out-Null

$dockerArgs = @(
    "run", "--rm",
    "-v", "${resultsDir}:/app/allure-results:ro",
    "-v", "${reportDir}:/app/allure-report",
    $allureImage,
    "allure", "generate", "/app/allure-results",
    "--clean", "-o", "/app/allure-report"
)

& docker @dockerArgs

if ($LASTEXITCODE -ne 0) {
    throw "Allure HTML generation failed."
}

if (-not (Test-Path (Join-Path $reportDir "index.html"))) {
    throw "Generated report index.html was not found."
}

# Ensure the existing viewer is available.
$viewer = docker ps -a --filter "name=^/$viewerName$" --format "{{.Names}}"

if (-not $viewer) {
    throw "Viewer container is missing. Recreate medishop-allure-viewer with the reports folder mounted."
}

$runningViewer = docker ps --filter "name=^/$viewerName$" --format "{{.Names}}"

if (-not $runningViewer) {
    docker start $viewerName | Out-Null

    if ($LASTEXITCODE -ne 0) {
        throw "Could not start the report viewer."
    }
}

Write-Host "Report generated successfully." -ForegroundColor Green
Start-Process "http://localhost:8080/local/"
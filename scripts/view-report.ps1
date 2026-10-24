
param(
    [Parameter(Mandatory = $true)]
    [ValidateSet("local", "github-actions")]
    [string]$Source
)

$ErrorActionPreference = "Stop"

$projectRoot = Split-Path -Parent $PSScriptRoot
$reportsRoot = Join-Path $projectRoot "reports"
$reportDir = Join-Path $reportsRoot $Source
$reportIndex = Join-Path $reportDir "index.html"
$viewerName = "medishop-allure-viewer"

# Validate that the selected report exists.
if (-not (Test-Path $reportIndex -PathType Leaf)) {
    throw "No HTML report found at '$reportDir'. Generate or extract the report first."
}

# Check whether the existing viewer is running.
$runningViewer = docker ps `
    --filter "name=^/$viewerName$" `
    --format "{{.Names}}"

if ($LASTEXITCODE -ne 0) {
    throw "Could not query Docker. Ensure Docker Desktop is running."
}

if (-not $runningViewer) {
    # Reuse the existing container if it is stopped.
    $existingViewer = docker ps -a `
        --filter "name=^/$viewerName$" `
        --format "{{.Names}}"

    if ($LASTEXITCODE -ne 0) {
        throw "Could not query Docker containers."
    }

    if ($existingViewer) {
        docker start $viewerName | Out-Null

        if ($LASTEXITCODE -ne 0) {
            throw "Could not start the existing report viewer."
        }
    }
    else {
        # Create the viewer with the reports directory mounted read-only.
        docker run -d `
            --name $viewerName `
            --restart unless-stopped `
            -p 8080:80 `
            -v "${reportsRoot}:/usr/share/nginx/html:ro" `
            nginx:alpine | Out-Null

        if ($LASTEXITCODE -ne 0) {
            throw "Could not create the report viewer."
        }
    }
}

# Open the selected report without regenerating it.
$url = "http://localhost:8080/$Source/"

Write-Host "Opening existing $Source report..." -ForegroundColor Green
Start-Process $url

$ErrorActionPreference = "Stop"

$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $repoRoot

$icsPath = Join-Path $repoRoot "docs\calendrier-liturgique-catholique-romain.ics"

if (Get-Command mvn -ErrorAction SilentlyContinue) {
    $mavenCommand = "mvn"
} elseif (Test-Path (Join-Path $repoRoot "mvnw.cmd")) {
    $mavenCommand = ".\\mvnw.cmd"
} elseif (Test-Path (Join-Path $repoRoot "mvnw")) {
    $mavenCommand = ".\\mvnw"
} else {
    Write-Host "ERREUR: Maven est introuvable." -ForegroundColor Red
    Write-Host "Installe Maven, ou ajoute le wrapper Maven (mvnw) dans le projet." -ForegroundColor Yellow
    exit 1
}

if (-not (Get-Command java -ErrorAction SilentlyContinue)) {
    Write-Host "ERREUR: Java est introuvable." -ForegroundColor Red
    Write-Host "Installe un JDK et assure-toi que la commande java est disponible dans PATH." -ForegroundColor Yellow
    exit 1
}

Write-Host "[1/3] Compilation Maven (test-compile)..." -ForegroundColor Cyan
& $mavenCommand clean test-compile

Write-Host "[2/3] Generation du calendrier..." -ForegroundColor Cyan
& $mavenCommand exec:java "-Dexec.mainClass=fr.plaisance.calit.FabricationCalendriers" "-Dexec.classpathScope=test"

Write-Host "[3/3] Verification du fichier genere..." -ForegroundColor Cyan
if (Test-Path $icsPath) {
    $fileInfo = Get-Item $icsPath
    Write-Host "OK: fichier genere -> $($fileInfo.FullName)" -ForegroundColor Green
    Write-Host "Taille: $($fileInfo.Length) octets | Modifie le: $($fileInfo.LastWriteTime)" -ForegroundColor Green
} else {
    Write-Host "ERREUR: fichier non trouve -> $icsPath" -ForegroundColor Red
    exit 1
}

Write-Host "Termine. Pense a commit/push pour publier sur GitHub Pages." -ForegroundColor Yellow

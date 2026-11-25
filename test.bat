@echo off
setlocal

cd /d "%~dp0"

if not exist "out\test-classes" mkdir "out\test-classes"

powershell -NoProfile -Command "Get-ChildItem -Path 'src/main','src/test' -Filter '*.java' -Recurse -File | ForEach-Object { '\"' + ($_.FullName.Substring((Get-Location).Path.Length + 1).Replace('\','/')) + '\"' } | Set-Content -Encoding ASCII 'out/sources.txt'"

if errorlevel 1 exit /b 1

javac -encoding UTF-8 -d "out\test-classes" @"out\sources.txt"

if errorlevel 1 exit /b 1

java -cp "out\test-classes" tests.AllTests

exit /b %errorlevel%

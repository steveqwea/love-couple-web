@echo off
cd /d %~dp0backend
echo ============================================
echo   Love Couple Web - Backend Server
echo   URL: http://localhost:8080
echo   Press Ctrl+C to stop
echo ============================================
"C:\Program Files\Java\jdk-17\bin\java" -jar target\love-backend-1.0.jar --server.port=8080
pause

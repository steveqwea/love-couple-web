@echo off
chcp 65001 >nul
title 恋爱小屋 - 公网部署
echo ========================================
echo    恋爱小屋 - 一键公网部署
echo ========================================
echo.

echo [1/3] 检查 MySQL...
tasklist /FI "IMAGENAME eq mysqld.exe" 2>nul | find "mysqld" >nul
if %errorlevel% neq 0 (
    echo     MySQL 未运行！请先启动 MySQL 服务
    pause
    exit /b 1
)
echo     MySQL 正常 ✓

echo [2/3] 检查后端服务...
tasklist /FI "IMAGENAME eq java.exe" 2>nul | find "java" >nul
if %errorlevel% neq 0 (
    echo     后端未运行，正在启动...
    cd /d "C:\Users\12659\Desktop\hanhan\love-couple-web\backend"
    start /b "" "C:\Program Files\Java\jdk-17\bin\java" -jar target\love-backend-1.0.jar --server.port=8080
    echo     等待后端启动（约15秒）...
    timeout /t 15 /nobreak >nul
    echo     后端已启动 ✓
) else (
    echo     后端已在运行 ✓
)
echo.

echo [3/3] 建立公网隧道（断开自动重连）...
echo     正在连接 localhost.run...
echo     保持此窗口开启即可，隧道断开会自动重连
echo.

:loop
echo.
echo  %date% %time% - 正在连接隧道...
ssh -o StrictHostKeyChecking=no -o ServerAliveInterval=30 -o ServerAliveCountMax=3 -i "%USERPROFILE%\.ssh\id_ed25519" -R 80:localhost:8080 localhost.run
echo.
echo  %date% %time% - 隧道已断开，5秒后自动重连...
timeout /t 5 /nobreak >nul
goto loop

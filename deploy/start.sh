#!/bin/bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17.0.20.0.8-1.1.0.2.1.al8.x86_64
export PATH=$JAVA_HOME/bin:$PATH

# Kill any existing instance
pkill -f 'love-backend' 2>/dev/null
sleep 1

# Create systemd service for auto-start on boot
cat > /etc/systemd/system/love-couple.service << 'SERVICE'
[Unit]
Description=Love Couple Web Application
After=network.target

[Service]
Type=simple
User=root
Environment=JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17.0.20.0.8-1.1.0.2.1.al8.x86_64
Environment=PORT=80
Environment=DB_URL=jdbc:mysql://gateway01.ap-northeast-1.prod.aws.tidbcloud.com:4000/love_db?sslMode=REQUIRED&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
Environment=DB_USER=GMUwM4qMwB9eU8q.root
Environment=DB_PASS=bx6aBR6pWSgZ2wA6
Environment=UPLOAD_DIR=/root/love-couple-web/uploads
ExecStart=/usr/lib/jvm/java-17-openjdk-17.0.20.0.8-1.1.0.2.1.al8.x86_64/bin/java -Xms128m -Xmx512m -jar /root/love-couple-web/backend/target/love-backend-1.0.jar
WorkingDirectory=/root/love-couple-web/backend
Restart=always
RestartSec=5

[Install]
WantedBy=multi-user.target
SERVICE

# Enable and start the service
systemctl daemon-reload
systemctl enable love-couple
systemctl start love-couple
sleep 3

# Check status
echo "=== Service Status ==="
systemctl status love-couple --no-pager 2>&1 | head -15

# Check if port 80 is listening
echo "=== Port Check ==="
ss -tlnp | grep ':80 '

# Test local access
echo "=== Local Test ==="
curl -s -o /dev/null -w "HTTP %{http_code}" http://localhost:80/ 2>&1
echo ""

# Open firewall port 80
echo "=== Firewall ==="
firewall-cmd --add-port=80/tcp --permanent 2>/dev/null && firewall-cmd --reload 2>/dev/null && echo "Firewall port 80 opened" || echo "Firewall not active or not installed"

#!/bin/bash
# Set Java 17 as default
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-17.0.20.0.8-1.1.0.2.1.al8.x86_64
alternatives --set java $JAVA_HOME/bin/java 2>/dev/null
alternatives --set javac $JAVA_HOME/bin/javac 2>/dev/null
export PATH=$JAVA_HOME/bin:$PATH

echo "=== Java Version ==="
java -version 2>&1
echo "=== Maven Version ==="
mvn -version 2>&1 | head -2

# Configure Maven to use Alibaba Cloud mirror for faster downloads
mkdir -p /root/.m2
cat > /root/.m2/settings.xml << 'XML'
<settings>
  <mirrors>
    <mirror>
      <id>aliyun</id>
      <name>Alibaba Cloud Mirror</name>
      <url>https://maven.aliyun.com/repository/public</url>
      <mirrorOf>central</mirrorOf>
    </mirror>
  </mirrors>
</settings>
XML
echo "=== Maven mirror configured ==="

# Build the project
cd /root/love-couple-web/backend
echo "=== Building JAR ==="
mvn package -DskipTests -q 2>&1 | tail -10
BUILD_EXIT=$?
echo "=== Build exit code: $BUILD_EXIT ==="

if [ $BUILD_EXIT -eq 0 ]; then
    ls -lh target/*.jar
    echo "=== BUILD SUCCESS ==="
else
    echo "=== BUILD FAILED ==="
fi

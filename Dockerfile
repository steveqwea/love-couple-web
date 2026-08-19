# 多阶段构建：Maven 编译 + 轻量 JRE 运行
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY backend/pom.xml .
RUN mvn dependency:go-offline -q
COPY backend/src ./src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Render 免费层 512MB 内存，限制 JVM 堆大小避免 OOM
ENTRYPOINT ["sh", "-c", "java -Xms64m -Xmx280m -jar app.jar"]

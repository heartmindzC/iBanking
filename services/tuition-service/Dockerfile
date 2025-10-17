#FROM openjdk:21
#WORKDIR /app
#COPY target/*.jar app.jar
#EXPOSE 8081
#ENTRYPOINT ["java","-jar","app.jar"]



# 1️⃣ Dùng image Maven để build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy toàn bộ mã nguồn vào container
COPY . .

# Build project và tạo file jar (bỏ qua test nếu muốn nhanh hơn)
RUN mvn clean package -DskipTests

# 2️⃣ Dùng image nhỏ hơn chỉ để chạy
FROM openjdk:21
WORKDIR /app

# Copy file jar đã build từ stage trên
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
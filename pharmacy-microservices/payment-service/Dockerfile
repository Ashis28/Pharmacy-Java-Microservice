FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY payment-service/target/payment-service-1.0.0.jar app.jar
EXPOSE 9095
ENTRYPOINT ["java", "-jar", "app.jar"]

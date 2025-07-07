FROM eclipse-temurin:17-jdk-alpine
# Crear directorio de trabajo dentro del contenedor
WORKDIR /app
# Copiar el JAR generado al contenedor
COPY target/VentaCliente-0.0.1-SNAPSHOT.jar app.jar
# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
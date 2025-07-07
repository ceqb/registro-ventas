# Imagen base con Java 17
FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el JAR generado por Maven
COPY target/*.jar app.jar

# Exponer el puerto (debe coincidir con server.port en application.yml)
EXPOSE 9002

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
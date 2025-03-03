# Usa la imagen base OpenJDK 17 con Alpine
FROM openjdk:17-jdk-alpine

# Define el argumento para el archivo JAR
ARG JAR_FILE=target/TresEnRaya-0.0.1-SNAPSHOT.jar

# Copia el archivo JAR generado a la imagen
COPY ${JAR_FILE} /app.jar

# Copia el directorio de recursos (incluido application.properties)
COPY src/main/resources/application.properties /application.properties

# Expon el puerto 8080 para la aplicación
EXPOSE 8080

# Comando de inicio que utiliza el archivo application.properties
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=classpath:/application.properties"]

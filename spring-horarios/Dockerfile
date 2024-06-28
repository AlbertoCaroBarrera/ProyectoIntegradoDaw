# Usa una imagen base de OpenJDK 17
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo WAR al contenedor
COPY target/spring-horarios-0.0.1-SNAPSHOT.war /app/spring-horarios-0.0.1-SNAPSHOT.war


# Exponer el puerto que usa la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "/app/spring-horarios-0.0.1-SNAPSHOT.war"]

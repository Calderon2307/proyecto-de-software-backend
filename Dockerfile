# -------- STAGE 1: Build del JAR --------
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos pom y descargamos dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copiamos el código fuente y construimos
COPY src ./src
RUN mvn -B clean package -DskipTests

# -------- STAGE 2: Runtime --------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el jar generado
COPY --from=build /app/target/*.jar app.jar

# Render por defecto usa PORT=10000
EXPOSE 10000

# Hacemos que Spring Boot escuche en el puerto que Render indique
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT:-10000} -jar app.jar"]

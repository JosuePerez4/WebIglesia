# --- Etapa 1: build ---
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copiamos primero el wrapper y el pom para aprovechar la cache de dependencias
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Ahora copiamos el resto del código y compilamos
COPY src ./src
RUN ./mvnw clean package -DskipTests -B

# --- Etapa 2: runtime ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Render inyecta la variable PORT; Spring Boot debe escuchar en ese puerto
ENV JAVA_OPTS=""
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar app.jar"]

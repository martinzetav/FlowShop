# 🧱 Etapa 1: Construcción
FROM eclipse-temurin:17.0.15_6-jdk AS build
WORKDIR /app

# Copiar el código fuente y wrapper
COPY microservice-order/.mvn/ .mvn
COPY microservice-order/mvnw microservice-order/pom.xml ./
COPY microservice-order/src ./src
COPY api-core /app/api-core

# Asegurar permisos del wrapper
RUN chmod +x mvnw

# Asegurar permisos y compilar api-core
RUN ./mvnw install -f api-core/pom.xml -DskipTests

# 👉 Compilar el microservicio auth
RUN ./mvnw clean package -DskipTests

# 🪶 Etapa 2: Imagen liviana para producción
FROM eclipse-temurin:17.0.15_6-jre AS runtime
WORKDIR /app

# Copiar solo el .jar compilado (nombre flexible)
COPY --from=build /app/target/*.jar app.jar

# INFORMAR EL PUERTO DONDE SE EJECUTA EL CONTENEDOR (INFORMATIVO)
EXPOSE 8092

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
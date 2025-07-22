# 🧱 Etapa 1: Construcción
FROM eclipse-temurin:17.0.15_6-jdk AS build
WORKDIR /app

# Copiar el código fuente y wrapper
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# Asegurar permisos del wrapper
RUN chmod +x mvnw

# Compilar la aplicación sin test
RUN ./mvnw clean package -DskipTests

# 🪶 Etapa 2: Imagen liviana para producción
FROM eclipse-temurin:17.0.15_6-jre AS runtime
WORKDIR /app

# Copiar solo el .jar compilado (nombre flexible)
COPY --from=build /app/target/*.jar app.jar

# INFORMAR EL PUERTO DONDE SE EJECUTA EL CONTENEDOR (INFORMATIVO)
EXPOSE 8888

# Ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
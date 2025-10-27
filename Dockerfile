# Stage 1: build (Maven + JDK 21)
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copia pom para aproveitar cache de dependências
COPY pom.xml ./
COPY .mvn .mvn
COPY mvnw mvnw
RUN mvn -B -f pom.xml dependency:go-offline

# Copia o código e faz o package
COPY src ./src
RUN mvn -B package -DskipTests

# Stage 2: runtime (JRE 21)
FROM eclipse-temurin:21-jre

# Cria usuário não-root
RUN addgroup --system appgroup && adduser --system appuser --ingroup appgroup
USER appuser

WORKDIR /app

# Copia o jar gerado do estágio de build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]

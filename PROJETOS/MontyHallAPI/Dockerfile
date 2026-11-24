# ==========================================================================
# ETAPA 1 - BUILD
# Imagem com Maven 3.9 + JDK 17 para compilar o projeto e gerar o .jar
# ==========================================================================
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copia primeiro só o pom.xml e baixa as dependências.
# Assim o Docker reaproveita este passo (cache) quando só o código muda.
COPY pom.xml .
RUN mvn -q -B dependency:go-offline

# Copia o código-fonte e gera o .jar (sem rodar testes)
COPY src ./src
RUN mvn -q -B clean package -DskipTests

# ==========================================================================
# ETAPA 2 - EXECUÇÃO
# Imagem menor, só com o Java 17 (JRE), para rodar o .jar
# ==========================================================================
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

# Mesma porta do application.properties (server.port=8080)
EXPOSE 8080

# MaxRAMPercentage: usa até 75% da memória do container
# (o plano gratuito do Render tem 512 MB)
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]

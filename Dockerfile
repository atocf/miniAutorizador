# Etapa de build
FROM openjdk:17-jdk-slim as build

WORKDIR /app

# Copiar o arquivo pom.xml e baixar as dependências
COPY pom.xml .
RUN apt-get update && apt-get install -y maven
RUN mvn dependency:go-offline

# Copiar o código-fonte e compilar a aplicação
COPY src ./src
RUN mvn package -DskipTests

# Criar a imagem final
FROM openjdk:17-jdk-slim

WORKDIR /app

# Instalar ferramentas necessárias
RUN apt-get update && apt-get install -y wget

# Baixar o script wait-for-it
RUN wget https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh -O /usr/local/bin/wait-for-it.sh \
    && chmod +x /usr/local/bin/wait-for-it.sh

# Copiar o arquivo JAR da etapa de compilação
COPY --from=build /app/target/*.jar app.jar

# Expor a porta 9080
EXPOSE 9080

# Comando para executar a aplicação
ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
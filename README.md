# Mini Autorizador VR

MiniAutorizadorVR é uma aplicação Spring Boot que simula o processo de autorização de transações de Vale Refeição e Vale Alimentação, garantindo a criação de cartões, consulta de saldo e autorização de transações conforme as regras definidas.

## Índice

- [Visão Geral](#visão-geral)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Configuração do Ambiente](#configuração-do-ambiente)
- [Execução da Aplicação Separadamente](#execução-da-aplicação-separadamente)
- [Swagger](#swagger)
- [Testes Automatizados](#testes-automatizados)
- [Cenários de Teste no Postman](#cenários-de-teste-no-postman)
- [Desafios e Considerações](#desafios-e-considerações)

## Visão Geral

Esta aplicação permite:
- Criação de cartões com saldo inicial de R$500,00
- Obtenção de saldo do cartão
- Autorização de transações realizadas usando os cartões previamente criados

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.3.1
- Maven
- MySQL
- MongoDB
- Docker
- Docker Compose
- Lombok
- JUnit 5
- Postman

## Configuração do Ambiente

1. Clone o repositório:
 ```bash
 git clone https://github.com/seu-usuario/mini-autorizador.git
 cd mini-autorizador
 ```

2. Configure o Docker e Docker Compose:

Certifique-se de ter o Docker e o Docker Compose instalados em sua máquina.

Inicie os serviços do Docker:
```bash
docker-compose up -d

```
Obs. Inclui a aplicação no Docker Compose para subir todo ambiente de validação. 

## Execução da Aplicação Separadamente

1.  Parar a aplicação que subiu via Docker Compose:
```bash
docker stop miniautorizador-app
```

2. Compile e execute a aplicação localmente:
```bash
mvn clean package java -jar target/miniAutorizador-0.0.1-SNAPSHOT.jar
```

## Swagger

[http://localhost:9080/swagger-ui/index.html#](http://localhost:9080/swagger-ui/index.html#)

## Testes Automatizados

Testes de Unidade
Os testes de unidade estão localizados no diretório src/test/java. Para executá-los, utilize o comando:
```bash
mvn test
```

## Cenários de Teste no Postman

Importe a coleção do Postman Mini Autorizador Tests e o ambiente Mini Autorizador Environment disponíveis no diretório postman.

### Cenários de Teste no Postman

Entrar em "Run Collection" e executar todos os testes, meno o teste este deve estar desabilitado:
- Testes de Concorrência/Realizar uma transação com sucesso para Concorrência

### Testes de Concorrência
Testes de Concorrência
Para testar a concorrência no Postman, você pode usar o Runner do Postman para executar várias instâncias da mesma requisição simultaneamente:
- Executar o "Testes de Concorrência/Realizar uma transação com sucesso para Concorrência" 
- Configurando para simular 2 usuarios ao mesmo tempo.


## Desafios e Considerações

### Desafios
- Construir a solução sem utilizar nenhum if, break ou continue.
- Garantir que duas transações disparadas ao mesmo tempo não causem problemas relacionados à concorrência.

### Considerações
- A aplicação foi desenvolvida utilizando boas práticas de desenvolvimento, incluindo a utilização de design patterns e princípios SOLID.
- A cobertura de testes está alta, garantindo que as classes sejam efetivamente testadas.
- Documentação detalhada foi incluída para facilitar a compreensão e manutenção do código.

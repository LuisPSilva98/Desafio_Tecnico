
Projeto de Crédito - Spring Boot + PostgreSQL + Kafka
======================================================

Este projeto é uma aplicação backend desenvolvida em Java 17 com Spring Boot 3.4.5,
usando Maven como gerenciador de dependências. A aplicação utiliza PostgreSQL como 
banco de dados e Kafka como sistema de mensageria, ambos orquestrados via Docker Compose.

Pré-requisitos
--------------
Certifique-se de ter as seguintes ferramentas instaladas:

- Java 17+: https://adoptium.net/en-GB/temurin/releases/
- Maven 3.8+: https://maven.apache.org/
- Docker: https://www.docker.com/
- Docker Compose: https://docs.docker.com/compose/

Subindo os serviços com Docker
------------------------------
1. Clone este repositório:

2. Suba os containers do Docker:

    docker-compose up -d

Os seguintes serviços serão iniciados:

- PostgreSQL (porta 5432)
- PgAdmin (porta 15432)
- Zookeeper
- Kafka (porta 9091)
- Kafdrop (UI do Kafka, porta 9000)

Configuração do Banco de Dados
------------------------------
- Host: localhost
- Porta: 5432
- Usuário: admin
- Senha: admin
- Database: creditdb

Gerencie o banco via PgAdmin acessando: http://localhost:15432

- Email: admin@admin.com
- Senha: admin

Execução da Aplicação
---------------------
Com os containers em execução, rode a aplicação com Maven:

    ./mvnw spring-boot:run

Ou compile o .jar:

    ./mvnw clean package
    java -jar target/seu-arquivo.jar

Acessando o Kafka
-----------------
A UI do Kafka (Kafdrop) estará disponível em: http://localhost:9000

- Broker configurado: localhost:9091

Variáveis de Ambiente (opcional)
--------------------------------
application.properties ou application.yml:

    spring.datasource.url=jdbc:postgresql://localhost:5432/creditdb
    spring.datasource.username=admin
    spring.datasource.password=admin

    spring.kafka.bootstrap-servers=localhost:9091

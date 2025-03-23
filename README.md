
API Backend - Sistema de Agendamento para Barbearia

📌 Descrição

Esta é uma API backend desenvolvida em Java utilizando o framework Spring Boot. O objetivo desta API é gerenciar um sistema de agendamento para uma barbearia, permitindo operações como:

Cadastro e gerenciamento de clientes 🧑‍💼

Cadastro e gerenciamento de barbeiros 💈

Criação e controle de agendamentos 📅

Listagem e filtragem de horários disponíveis 🕒

🛠️ Tecnologias Utilizadas

Java 17+ - Linguagem de programação principal

Spring Boot - Framework para criação da API

Maven - Gerenciador de dependências

Spring Data JPA - Camada de persistência

H2 Database / PostgreSQL - Banco de dados (H2 para testes, PostgreSQL para produção)

Spring Web - Construção da API REST

JUnit & Mockito - Testes unitários

📂 Estrutura do Projeto

O projeto segue uma estrutura organizada em pacotes:

 * barbearia_backend/
 * src/
 * main/
 * java/com/barbearia/
 * models/        # Modelos das entidades
 * dto/           # Data Transfer Objects
 * repository/    # Repositórios JPA
 * service/       # Lógica de negócios
 * controller/    # Endpoints da API
 * exceptions/    # Tratamento de exceções
 * resources/
 ** application.properties  # Configurações da aplicação
 ** application-dev.properties  # Configuração do ambiente de desenvolvimento
 ** application-test.properties  # Configuração do ambiente de testes
 * test / # Criação de alguns testes unitários no pacote service
  

⚙️ Configuração e Execução

🔹 Clonar o Repositório
git clone https://github.com/daniellebarbosa81/barbearia_backend.git
cd barbearia_backend

🔹 Configurar o Banco de Dados

Por padrão, o projeto está configurado para usar o H2 Database no ambiente de desenvolvimento e testes. Para mudar para PostgreSQL, altere o application.properties.

* H2 Database  #Ambiente de Desenvolvimento e Testes#
(spring.datasource.url=jdbc:h2:mem:barbearia_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console)

* PostgreSQL # Produção # 
(spring.datasource.url=jdbc:postgresql://localhost:5432/barbearia_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update)

🔹 Executar a Aplicação

* Para rodar a API localmente, use o seguinte comando:
   mvn spring-boot:run

🔹 A API estará disponível em http://localhost:8080

🔹 Acessar o Banco de Dados (H2 Console)

🔹 Se estiver usando o H2 Database, acesse:

 http://localhost:8080/h2-console

Use a URL jdbc:h2:mem:barbearia_db para conectar.

🚀 Endpoints da API

🔹 Cliente

POST /clientes → Cadastrar um novo cliente

GET /clientes → Listar todos os clientes

GET /clientes/{id} → Buscar cliente por ID

PUT /clientes/{id} → Atualizar dados de um cliente

DELETE /clientes/{id} → Remover um cliente

🔹 Barbeiro

POST /barbeiros → Cadastrar um barbeiro

GET /barbeiros → Listar barbeiros disponíveis

GET /barbeiros/{id} → Buscar barbeiro por ID

🔹 Agendamento

POST /agendamentos → Criar um novo agendamento

GET /agendamentos → Listar todos os agendamentos

GET /agendamentos/{id} → Buscar agendamento por ID

DELETE /agendamentos/{id} → Cancelar um agendamento

✅ Testes

* Os testes são feitos utilizando JUnit e Mockito. Para executá-los, utilize:

 mvn test

📜 Licença

Este projeto está licenciado Mit License







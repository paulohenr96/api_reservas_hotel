# README - Hotel Management System

Este projeto é um sistema de gerenciamento de hotel que inclui dois controllers: "QuartoController" e "ReservaController". Esses controllers lidam com as operações relacionadas a quartos e reservas, respectivamente.

## Tecnologias utilizadas

- Java 11
- Spring Boot
- Spring Data
- Testes unitários com JUnit 5 e Mockito
- Lombok

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter os seguintes requisitos atendidos:

- Ambiente de desenvolvimento configurado com as dependências necessárias.
- Banco de dados configurado e acessível.
- Configurações corretas do servidor web.

## Executando o projeto

Siga as etapas abaixo para executar o projeto:

1. Clone o repositório para a sua máquina local.
2. Importe o projeto na sua IDE de desenvolvimento.
3. Configure as dependências e as configurações do banco de dados conforme necessário.
4. Inicie o servidor web.
5. Acesse os endpoints fornecidos pelos controllers usando um cliente HTTP.

## Autenticação 
Esta api possui autenticação JWT. A classe JWTCreator possui dois métodos estáticos. O primeiro tem a função de criar um token novo e o segundo tem a função de autenticar o token inserido pelo usuario e retornar um objeto contendo o username e as roles do usuario. O Filtro JWTFilter é ativado a cada resuisição do usuário para avaliar a autenticidade do token.

## Controllers

### QuartoController

O QuartoController fornece os seguintes endpoints para gerenciar os quartos:

- **POST /quartos**: Salva um novo quarto.
- **DELETE /quartos/{idQuarto}**: Deleta um quarto com base no ID fornecido.
- **GET /quartos**: Retorna todos os quartos em páginas, com suporte para paginação.
- **GET /quartos/disponiveis**: Retorna todos os quartos disponíveis em uma determinada data, em páginas, com suporte para paginação.
- **GET /quartos/{idQuarto}**: Retorna os detalhes de um quarto específico com base no ID fornecido.
- **PUT /quartos/{idQuarto}**: Atualiza os detalhes de um quarto específico com base no ID fornecido.

### ReservaController

O ReservaController fornece os seguintes endpoints para gerenciar as reservas:

- **POST /reservas**: Cria uma nova reserva para um quarto específico.
- **GET /reservas**: Retorna todas as reservas em páginas, com suporte para paginação.
- **GET /reservas/pordata**: Retorna todas as reservas em uma determinada data, em páginas, com suporte para paginação.
- **GET /reservas/porquarto/{quarto}**: Retorna todas as reservas de um determinado quarto, em páginas, com suporte para paginação.
- **GET /reservas/{idReserva}**: Retorna os detalhes de uma reserva específica com base no ID fornecido.
- **DELETE /reservas/{idReserva}**: Deleta uma reserva com base no ID fornecido.
- **PUT /reservas/{idReserva}**: Atualiza os detalhes de uma reserva específica com base no ID fornecido.
### LoginController

O login Controller possui apenas um endpoint.
- **POST /login**: Recebe um dto contendo o username e a password e retorna um DTO contendo o token,username e data de expiração.

### SWAGGER
A aplicação contém o swagger para facilitar os testes. Para usa-lo é recomendado desabilitar o token do JWT no arquivo de configuração do Spring Security (WebConfigSecurity)
![image](https://github.com/paulohenr96/api_reservas_hotel/assets/89654592/aa67c26d-2a8a-42b4-a66f-c7de39fb7b1e)


## Testes unitários

O projeto inclui testes unitários escritos com JUnit 5 e Mockito. Os testes são implementados para garantir o bom funcionamento dos controllers e serviços.

## Contribuição

Contribuições são bem-vindas! Se você encontrar algum problema, tiver sugestões de melhorias ou desejar adicionar novos recursos ao projeto, fique à vontade para abrir uma issue ou enviar um pull request.

## Autor

Paulo Henrique dos Santos


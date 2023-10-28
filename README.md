# API Kotlin Bancária

A API Kotlin Bancária é um aplicativo de servidor desenvolvido em Kotlin que fornece serviços bancários. Ele inclui rotas para criar, recuperar, atualizar e excluir contas bancárias.

## Destaques

- **Arquitetura MVC**: O código segue o padrão de arquitetura Model-View-Controller (MVC) para manter a organização e escalabilidade do projeto.
- **Spring Boot**: A API é construída usando o framework Spring Boot para simplificar o desenvolvimento e a manutenção.
- **H2 Database (Cache Temporário)**: Utiliza um banco de dados H2 para armazenar dados em cache.
- **Testes Unitários**: A API inclui testes unitários para garantir a qualidade e confiabilidade do código.

## Rotas Disponíveis

- **POST /accounts**: Cria uma nova conta bancária. Deve fornecer um corpo JSON com os detalhes da conta.
- **GET /accounts**: Recupera a lista de todas as contas bancárias.
- **GET /accounts/id/{id}**: Recupera uma conta bancária pelo ID.
- **GET /accounts/name/{name}**: Recupera contas bancárias pelo nome do titular.
- **PUT /accounts/{id}**: Atualiza uma conta bancária existente pelo ID. Deve fornecer um corpo JSON com os detalhes atualizados da conta.
- **DELETE /accounts/{id}**: Exclui uma conta bancária pelo ID.

## Pré-requisitos

- Ambiente de desenvolvimento Java e Kotlin.
- Gerenciador de pacotes Gradle ou Maven.
- IDE de sua escolha (recomenda-se o uso do IntelliJ IDEA).

## Configuração e Execução

1. Clone este repositório: `git clone https://github.com/wallissonmart/api-rest-kotlin.git`.
2. Abra o projeto em sua IDE.
3. Certifique-se de que o banco de dados H2 está configurado corretamente.
4. Execute a aplicação Spring Boot.
5. Acesse as rotas fornecidas conforme necessário.

## Contribuição

Se desejar contribuir para o projeto, siga estas etapas:

1. Fork o projeto.
2. Crie sua ramificação de recurso (`git checkout -b feature/SeuRecurso`).
3. Commit suas alterações (`git commit -m 'Adicionar novo recurso'`).
4. Faça push para a ramificação (`git push origin feature/SeuRecurso`).
5. Abra uma solicitação pull.

## Autor

[Wallisson Martins] - [wallissonmartins37@gmail.com]

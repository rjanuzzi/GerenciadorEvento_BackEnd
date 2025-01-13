# Backend (Spring Boot) - API de Gestão de Eventos

Este projeto contém os serviços para login e cadastro de administradores, bem como para a gestão de eventos, utilizando Spring Boot. Ele inclui autenticação via JWT, criptografia de senhas, e documentação com Swagger.

## Funcionalidades

### 1. Serviço de Login de Administrador
- **Descrição**: Autentica um administrador com base no email e senha fornecidos.
- **Entradas**: 
  - `email`: Email do administrador.
  - `senha`: Senha do administrador.
- **Saídas**: Retorna um token JWT que deve ser usado nos demais serviços.

### 2. Serviço de Cadastro de Administrador
- **Descrição**: Permite o cadastro de um novo administrador.
- **Entradas**: 
  - `nome`: Nome completo do administrador.
  - `email`: Email do administrador (deve ser único).
  - `senha`: Senha do administrador.
- **Saídas**: Armazena a senha de forma criptografada no banco de dados.

### 3. Serviço de Listagem de Eventos
- **Descrição**: Retorna todos os eventos associados a um administrador.
- **Entradas**: 
  - `adminId`: ID do administrador.
- **Saídas**: Lista de eventos associados ao administrador especificado.

### 4. Serviço de Cadastro de Evento
- **Descrição**: Permite o cadastro de um novo evento, associando-o a um administrador.
- **Entradas**: 
  - `nome`: Nome do evento.
  - `data`: Data do evento.
  - `localizacao`: Localização do evento.
  - `imagem`: URL da imagem do evento.
  - `adminId`: ID do administrador que cria o evento.
- **Saídas**: Cria e associa o evento ao administrador especificado.

### 5. Serviço de Atualização de Evento
- **Descrição**: Permite a atualização da data ou localização de um evento existente.
- **Entradas**:
  - `eventoId`: ID do evento a ser atualizado.
  - `data` (opcional): Nova data do evento.
  - `localizacao` (opcional): Nova localização do evento.
- **Saídas**: Atualiza o evento com as informações fornecidas.

### 6. Serviço de Exclusão de Evento
- **Descrição**: Exclui um evento com base no seu ID.
- **Entradas**:
  - `eventoId`: ID do evento a ser excluído.
- **Saídas**: Evento excluído do sistema.

## Segurança
- **JWT**: Todos os serviços, exceto o login, exigem um token JWT para autenticação.
- **Criptografia de Senha**: A senha do administrador é armazenada de forma criptografada utilizando técnicas seguras.

## Documentação da API
- A API está documentada com **Swagger** (Spring Fox), permitindo a fácil exploração dos endpoints e das operações disponíveis.
- Para acessar a documentação, basta iniciar a aplicação e navegar até `/swagger-ui.html`.

## Dependências do Projeto

- `spring-boot-starter-data-jpa`
- `spring-boot-starter-security`
- `spring-boot-starter-validation`
- `spring-boot-starter-web`
- `spring-boot-devtools`
- `spring-boot-starter-test`
- `flyway-core`
- `flyway-database-postgresql`
- `postgresql`
- `springdoc-openapi-starter-webmvc-ui`
- `jjwt-api`
- `jjwt-impl`
- `jjwt-jackson`
- `spring-security-test`

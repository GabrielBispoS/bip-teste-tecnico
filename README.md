# Desafio Técnico Bip Brasil - Fullstack Java + Angular

Repositório com a solução desenvolvida para o desafio técnico de Pessoa Desenvolvedora Fullstack (Java + Angular). 

O projeto consiste na correção de um bug de concorrência num serviço EJB legado, além do desenvolvimento completo das camadas de Backend (API REST) e Frontend (SPA).

## Tecnologias Utilizadas
- **Backend:** Java 17, Spring Boot 3, EJB (Jakarta EE), Spring Data JPA, H2 Database, Swagger (OpenAPI).
- **Frontend:** Angular 17+ (Standalone Components), TypeScript, HTML5, CSS3.
- **Ferramentas:** Maven, Git.

## Arquitetura e Soluções Implementadas

### 1. Correção do EJB (Controlo de Concorrência)
O serviço original `BeneficioEjbService` apresentava falhas críticas de consistência em transferências financeiras (ausência de validação de saldo e vulnerabilidade a *lost updates*). 
A solução incluiu:
- **Optimistic Locking:** Implementação da anotação `@Version` na entidade JPA para prevenir sobrescritas concorrentes. Qualquer tentativa de alteração simultânea no mesmo registo lança uma `OptimisticLockException`.
- **Validações de Negócio:** Bloqueio de transferências com valores negativos ou superiores ao saldo disponível.
- **Controlo Transacional:** Utilização de `@TransactionAttribute(TransactionAttributeType.REQUIRED)` para garantir o *rollback* automático em caso de qualquer falha durante a operação.

### 2. Backend (Spring Boot)
- Estruturação em camadas (Controller, Service/EJB, Repository, Model).
- Exposição de um CRUD completo para a entidade `Beneficio`.
- Integração fluida entre o Spring Boot e o módulo EJB.
- Tratamento de exceções e retorno de códigos HTTP adequados (ex: 400 Bad Request, 404 Not Found).
- Documentação automática da API configurada com Swagger.

### 3. Frontend (Angular)
- Desenvolvimento de uma interface limpa para listar os saldos e realizar novas transferências.
- Comunicação assíncrona com o Backend via `HttpClient` (RxJS).
- Tratamento do estado da interface, exibindo mensagens de sucesso ou erro com base no retorno da API.

---

## Como executar o projeto

Para facilitar a avaliação e garantir a consistência do ambiente, a aplicação foi dockerizada.

### Pré-requisitos
- Docker e Docker Compose instalados.

### Passos para execução
1. Clone o repositório.

2. Na raiz do projeto, execute o comando:
   ```bash
   docker compose up -d --build

3. Acesse as aplicações:
    Frontend (Angular): http://localhost:4200
    
    Backend (Swagger API): http://localhost:8080/swagger-ui.html
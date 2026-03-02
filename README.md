# Desafio Técnico Bip Brasil - Fullstack Java + Angular

Repositório com a solução desenvolvida para o desafio técnico de Pessoa Desenvolvedora Fullstack (Java + Angular). 

O projeto consiste na correção de um bug de concorrência num serviço EJB legado, além do desenvolvimento completo das camadas de Backend (API REST) e Frontend (SPA).

## Diferenciais Implementados (Seniority Level)
Além dos requisitos básicos, foram aplicadas as seguintes práticas de engenharia:
- **Dockerização Completa:** Orquestração de ambiente via Docker Compose para garantir consistência no build.
- **CI/CD Pipeline:** Integração contínua configurada via GitHub Actions (.github/workflows) para automação de testes e builds.
- **Observabilidade:** Implementação do **Spring Boot Actuator** para monitoramento de métricas de performance (CPU, Memória, Health).
- **Testes Automatizados:** Cobertura de testes unitários no módulo EJB com JUnit 5 e Mockito, validando regras de negócio e integridade transacional.

## Arquitetura e Soluções Implementadas

### 1. Correção do EJB (Controlo de Concorrência)
O serviço `BeneficioEjbService` foi refatorado para garantir consistência em ambientes de alta concorrência:
- **Optimistic Locking:** Uso de `@Version` na entidade JPA para prevenir *lost updates*.
- **Validações:** Bloqueio de transferências inconsistentes (valores negativos ou superiores ao saldo).
- **Controlo Transacional:** Garantia de atomicidade através de `@TransactionAttribute`.

### 2. Backend (Spring Boot) & Frontend (Angular)
- **Backend:** API REST documentada, estruturada em camadas e com tratamento global de exceções.
- **Frontend:** SPA em Angular 17 utilizando **Standalone Components** e organização por domínios (Components, Services, Models).

## Visão de Evolução: Mensageria & Microsserviços

Considerando os requisitos de alta disponibilidade e desacoplamento:
1.  **Event-Driven:** A arquitetura está preparada para substituir a chamada síncrona entre Backend e EJB por um modelo de mensageria via **RabbitMQ** ou **Kafka**.
2.  **Escalabilidade Horizontal:** O uso de EJB isolado permite que o processamento de benefícios seja escalado independentemente da API de interface.


---

## Como executar o projeto

### Pré-requisitos
- Docker e Docker Compose instalados.

### Passos para execução
1. Clone o repositório.
2. Na raiz do projeto, execute:
   ```bash
   docker compose up -d --build

## Endpoints Principais
   Frontend (Angular): http://localhost:4200

   Documentação API (Swagger): http://localhost:8080/swagger-ui.html

   Métricas de Performance (Actuator): http://localhost:8080/actuator/metrics
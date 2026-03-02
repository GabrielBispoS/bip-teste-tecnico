# Desafio Técnico Bip Brasil - Fullstack Java + Angular

Solução robusta desenvolvida para o desafio de Pessoa Desenvolvedora Fullstack, focada em escalabilidade, integridade transacional e padrões de arquitetura moderna.

## Arquitetura Proposta (Cloud-Ready)

O projeto foi estruturado seguindo princípios de **Microsserviços**, separando a lógica de negócio pesada (EJB) da camada de interface com o usuário (Backend API).

- **EJB-Module (Core Business):** Responsável pelas regras críticas de saldo e transferências.
- **Backend-Module (Edge API):** Atua como o Gateway de entrada, gerenciando requisições REST e expondo métricas de saúde.
- **Frontend-Module (Angular SPA):** Interface reativa com feedback instantâneo e tratamento de estados de carregamento (Async UI).



## Diferenciais de Engenharia (Seniority Level)

Além dos requisitos básicos, foram aplicadas práticas para ambientes de missão crítica:

* **Resiliência no Build:** Orquestração via Docker Compose com separação de estágios (*multi-stage build*) para garantir imagens leves e seguras.
* **Observabilidade:** Implementação do **Spring Boot Actuator** permitindo monitoramento em tempo real (Health, Metrics, Info).
* **Testes de Regressão:** Cobertura de testes unitários no módulo EJB com **JUnit 5 e Mockito**, validando cenários de sucesso, saldo insuficiente e concorrência.
* **UX Reativa:** Frontend implementado com `ChangeDetectorRef` e *loading states* para lidar com o tempo de boot de ambientes distribuídos.

## Visão de Evolução: Mensageria & Microsserviços

Considerando os requisitos de alta disponibilidade e desacoplamento:
1.  **Event-Driven:** A arquitetura está preparada para substituir a chamada síncrona entre Backend e EJB por um modelo de mensageria via **RabbitMQ** ou **Kafka**.
2.  **Escalabilidade Horizontal:** O uso de EJB isolado permite que o processamento de benefícios seja escalado independentemente da API de interface.

---

## Como Executar o Projeto

### Pré-requisitos
- Docker e Docker Compose instalados.

### Passos para execução
1. Clone o repositório.

2. Na raiz do projeto, execute o comando para build limpo:
   ```bash
   docker compose up -d --build

### Endpoints Principais

   Frontend (Angular): http://localhost:4200

   Documentação API (Swagger): http://localhost:8080/swagger-ui.html

   Saúde do Sistema (Actuator): http://localhost:8080/actuator/health
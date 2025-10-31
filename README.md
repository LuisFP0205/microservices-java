# Microservices Java

Este projeto é uma implementação de uma arquitetura de **microsserviços** utilizando **Java** e o ecossistema **Spring Boot/Spring Cloud**. O objetivo é demonstrar a comunicação, descoberta de serviços, roteamento, autenticação e conversão de moeda entre diferentes serviços.

## Tecnologias Principais

*   **Java 17+**
*   **Spring Boot**
*   **Spring Cloud** (Config Server, Eureka Discovery, Gateway)
*   **Spring Security** (para autenticação no `auth-service`)
*   **Feign Client** (para comunicação inter-serviços)
*   **PostgreSQL** (banco de dados, inferido pela prática comum em projetos Spring)
*   **Docker** e **Docker Compose** (para orquestração e ambiente de desenvolvimento)

## Estrutura do Projeto (Microsserviços)

O projeto é composto pelos seguintes microsserviços, cada um com uma responsabilidade específica:

| Microsserviço | Descrição |
| :--- | :--- |
| `config-service` | Servidor de configuração centralizado para gerenciar as propriedades de todos os microsserviços. |
| `discovery-service` | Servidor de descoberta de serviços (Eureka Server) para registro e localização de instâncias de microsserviços. |
| `gateway-service` | Gateway de API (Spring Cloud Gateway) para roteamento de requisições, segurança e balanceamento de carga. |
| `auth-service` | Serviço de autenticação e autorização, responsável por registrar novos usuários e gerar tokens JWT. |
| `currency-service` | Serviço de conversão de moeda, utilizando dados do Banco Central do Brasil (BCB) e cache. |
| `greeting-service` | Serviço simples de saudação para demonstração. |
| `product-service` | Serviço de gerenciamento de produtos, com endpoints públicos e protegidos (administrador). |
| `order-service` | Serviço de gerenciamento de pedidos, que interage com o `product-service` e `currency-service`. |

## Endpoints dos Microsserviços

A seguir, estão detalhados os endpoints de cada microsserviço, incluindo o método HTTP, o caminho e a função.

### 1. `auth-service` (Autenticação)

Base Path: `/auth`

| Método | Path | Função |
| :--- | :--- | :--- |
| `POST` | `/auth/signup` | **Registro de Usuário.** Cria um novo usuário comum (`UserType.Common`). |
| `POST` | `/auth/signin` | **Login de Usuário.** Autentica o usuário com e-mail e senha e retorna um token JWT. |

### 2. `currency-service` (Conversão de Moeda)

Base Path: `/currency`

| Método | Path | Função |
| :--- | :--- | :--- |
| `GET` | `/currency/{value}/{source}/{target}` | **Conversão de Moeda.** Converte um `value` da moeda de origem (`source`) para a moeda de destino (`target`). Utiliza cache e API do BCB. |

### 3. `greeting-service` (Saudação)

Base Path: `/greeting`

| Método | Path | Função |
| :--- | :--- | :--- |
| `GET` | `/greeting` | **Saudação Padrão.** Retorna uma saudação. Pode receber um `name` via `RequestParam`. |
| `GET` | `/greeting/{namePath}` | **Saudação com Path.** Retorna uma saudação usando o nome fornecido no path. |
| `POST` | `/greeting` | **Saudação com Body.** Retorna uma saudação usando o nome fornecido no corpo da requisição (DTO). |

### 4. `product-service` (Produtos)

O `product-service` possui dois controladores: um para acesso público (`/products`) e outro para acesso protegido (`/ws/products`).

#### 4.1. Acesso Público (`OpenProductController`)

Base Path: `/products`

| Método | Path | Função |
| :--- | :--- | :--- |
| `GET` | `/products/{idProduct}/{targetCurrency}` | **Detalhe do Produto com Conversão.** Busca um produto por ID e converte seu preço para a moeda de destino (`targetCurrency`). Utiliza cache. |
| `GET` | `/products/noconverter/{idProduct}` | **Detalhe do Produto (Sem Conversão).** Busca um produto por ID sem realizar a conversão de moeda. |
| `GET` | `/products/{targetCurrency}` | **Listagem de Produtos com Conversão.** Lista todos os produtos paginados, convertendo o preço de cada um para a moeda de destino (`targetCurrency`). |

#### 4.2. Acesso Protegido (`WsProductController`)

Base Path: `/ws/products` (Requer autenticação e headers de usuário)

| Método | Path | Função | Requisitos |
| :--- | :--- | :--- | :--- |
| `POST` | `/ws/products` | **Criação de Produto.** Adiciona um novo produto. | `X-User-Type` deve ser `0` (Administrador). |
| `PUT` | `/ws/products/{idProduct}` | **Atualização de Produto.** Atualiza um produto existente por ID. | `X-User-Type` deve ser `0` (Administrador). |
| `DELETE` | `/ws/products/{idProduct}` | **Exclusão de Produto.** Remove um produto por ID. | `X-User-Type` deve ser `0` (Administrador). |

### 5. `order-service` (Pedidos)

Base Path: `/ws/orders` (Requer autenticação e headers de usuário)

| Método | Path | Função | Requisitos |
| :--- | :--- | :--- | :--- |
| `POST` | `/ws/orders` | **Criação de Pedido.** Cria um novo pedido para o usuário autenticado. Interage com `product-service` para obter detalhes dos itens. | Requer `X-User-Id`, `X-User-Email`, `X-User-Type` nos headers. |
| `GET` | `/ws/orders/{targetCurrency}` | **Listagem de Pedidos.** Lista os pedidos do usuário autenticado, convertendo os valores para a moeda de destino (`targetCurrency`). | Requer `X-User-Id`, `X-User-Email`, `X-User-Type` nos headers. |

## Como Executar o Projeto

Para rodar o projeto, você precisará ter o **Docker** e **Docker Compose** instalados.

1.  **Clonar o Repositório:**
    ```bash
    git clone https://github.com/LuisFP0205/microservices-java.git
    cd microservices-java
    ```

2.  **Configurar o Ambiente:**
    O projeto utiliza um arquivo `docker-compose.yml` para orquestrar os serviços.

3.  **Build e Execução (Usando Docker Compose):**
    Execute o comando abaixo no diretório raiz do projeto (`microservices-java`):
    ```bash
    docker-compose up --build
    ```
    Este comando irá:
    *   Construir as imagens Docker para cada microsserviço (se necessário).
    *   Iniciar todos os contêineres definidos no `docker-compose.yml`, incluindo o Eureka Server (`discovery-service`), Config Server (`config-service`), o Gateway (`gateway-service`) e os microsserviços de negócio.

4.  **Acesso:**
    O `gateway-service` é o ponto de entrada para todos os microsserviços. Ele estará acessível em `http://localhost:8080` (a porta pode variar, verifique o `docker-compose.yml`).

    **Exemplos de Acesso via Gateway:**
    *   **Auth Service:** `http://localhost:8080/auth/signup`
    *   **Greeting Service:** `http://localhost:8080/greeting/world`
    *   **Currency Service:** `http://localhost:8080/currency/100/USD/BRL`
    *   **Product Service (Público):** `http://localhost:8080/products/1/EUR`

---
**Desenvolvido por:** Luis Felipe Pagnussat

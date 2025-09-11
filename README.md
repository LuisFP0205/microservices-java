# Greeting Microservice

Este projeto é um microserviço simples construído com **Spring Boot** que oferece diferentes endpoints para gerar saudações personalizadas. Ele demonstra como lidar com requisições `GET` e `POST` em uma API RESTful.

---

## ✨ Funcionalidades

- **Saudação por Path Variable:** Gera uma saudação passando o nome diretamente na URL.  
- **Saudação por Request Parameter:** Gera uma saudação passando o nome como parâmetro de consulta (`?name=`).  
- **Saudação por JSON Body (POST):** Permite enviar o nome no corpo da requisição `POST` em formato JSON.  

---

## 🛠 Tecnologias Utilizadas

- **Java 17+**  
- **Spring Boot 3.2.x**  
- **Maven** (gerenciamento de dependências e build)  

---

## 🚀 Como Começar

### Pré-requisitos

Certifique-se de ter o seguinte instalado em sua máquina:

- [Java Development Kit (JDK) 17+](https://adoptium.net/)  
- [Git](https://git-scm.com/)  
- [Maven](https://maven.apache.org/)  

### Instalação

1. Clone este repositório para sua máquina local:

   ```bash
   git clone https://github.com/LuisFP0205/microservices-java.git
   cd microservices-java
   ```

2. Compile a aplicação com Maven:

   ```bash
   mvn clean install
   ```

3. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080)

---

## 📌 Endpoints da API

Todos os endpoints estão disponíveis sob a rota base: `/greeting`.

---

### 1️⃣ GET por Path Variable

- **URL:** `GET http://localhost:8080/greeting/{nome}`  
- **Exemplo:**  
  ```
  http://localhost:8080/greeting/Leia
  ```
- **Resposta:**  
  ```
  Ciao, Leia!!!
  ```

---

### 2️⃣ GET por Request Parameter

- **URL:** `GET http://localhost:8080/greeting?name={nome}`  
- **Descrição:** Se o parâmetro `name` não for informado, será usado um nome padrão.  
- **Exemplo:**  
  ```
  http://localhost:8080/greeting?name=Leia
  ```
- **Resposta:**  
  ```
  Ciao, Leia!!!
  ```

---

### 3️⃣ POST com JSON Body

- **URL:** `POST http://localhost:8080/greeting`  
- **Exemplo usando cURL:**  
  ```bash
  curl -X POST http://localhost:8080/greeting        -H "Content-Type: application/json"        -d '{"name": "Leia"}'
  ```
- **Corpo da Requisição (JSON):**
  ```json
  {
    "name": "Leia"
  }
  ```
- **Resposta:**
  ```
  Ciao, Leia!!!
  ```

# Microservices Java

Este repositório contém um conjunto de microserviços desenvolvidos em **Java com Spring Boot**, com foco em comunicação entre serviços, boas práticas de arquitetura e exposição de APIs REST.

---

## 📌 Estrutura do Projeto

* **config-service** → Centraliza e distribui configurações para os demais serviços.
* **greeting-service** → Exemplo simples de geração de saudações via API REST.
* **currency-service** → Serviço para manipulação e conversão de moedas.
* **product-service** → Serviço para gerenciamento de produtos.

Cada serviço pode ser iniciado de forma independente, mas juntos compõem uma arquitetura de microsserviços.

---

## 🛠️ Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot**
* **Maven**
* **APIs REST (Spring Web)**
* **Spring Cloud** (para configuração e comunicação entre serviços)

---

## 🚀 Executando o Projeto

### Pré-requisitos

* JDK 17 ou superior
* Maven instalado

### Passos

1. Clone o repositório:

   ```bash
   git clone https://github.com/LuisFP0205/microservices-java.git
   cd microservices-java
   ```

2. Compile os projetos:

   ```bash
   mvn clean install
   ```

3. Inicie o microserviço desejado, por exemplo o **greeting-service**:

   ```bash
   cd greeting-service
   mvn spring-boot:run
   ```

---


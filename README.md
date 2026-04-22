# Car Rental API

API REST para gerenciamento de locação de veículos, desenvolvida com Java 21 e Spring Boot.

## Sobre o Projeto

Sistema de locação de carros que permite o cadastro de veículos, categorias e usuários, além do gerenciamento completo do ciclo de locação — desde a criação até a devolução ou cancelamento, com cálculo automático de multas por atraso.

## Tecnologias

- **Java 21**
- **Spring Boot 3.3**
- **Spring Data JPA**
- **PostgreSQL**
- **MapStruct**
- **Lombok**
- **Bean Validation**
- **SpringDoc OpenAPI (Swagger)**
- **Docker + Docker Compose**

## Arquitetura

```
src/main/java/com/empresa/carRental/
├── config/
├── controller/
├── service/
├── repository/
├── domain/
│   ├── entity/
│   └── enums/
├── dto/
│   ├── request/
│   └── response/
├── mapper/
└── exception/
```

## Entidades

| Entidade | Descrição |
|----------|-----------|
| `User` | Clientes que realizam locações |
| `Car` | Veículos disponíveis para locação |
| `Category` | Categorias dos veículos (ECONOMY, STANDARD, SUV, LUXURY) |
| `Rental` | Registro de locações com status e valores |

## Endpoints

### Users
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/v1/users` | Cadastrar usuário |
| GET | `/api/v1/users` | Listar usuários |
| GET | `/api/v1/users/{id}` | Buscar por ID |
| PUT | `/api/v1/users/{id}` | Atualizar usuário |
| DELETE | `/api/v1/users/{id}` | Inativar usuário |

### Cars
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/v1/cars` | Cadastrar carro |
| GET | `/api/v1/cars` | Listar carros |
| GET | `/api/v1/cars/{id}` | Buscar por ID |
| GET | `/api/v1/cars/available` | Listar carros disponíveis |
| PUT | `/api/v1/cars/{id}` | Atualizar carro |
| PATCH | `/api/v1/cars/{id}/status` | Alterar status |
| DELETE | `/api/v1/cars/{id}` | Inativar carro |

### Categories
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/v1/categories` | Cadastrar categoria |
| GET | `/api/v1/categories` | Listar categorias |
| GET | `/api/v1/categories/{id}` | Buscar por ID |
| PUT | `/api/v1/categories/{id}` | Atualizar categoria |
| DELETE | `/api/v1/categories/{id}` | Inativar categoria |

### Rentals
| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/v1/rentals` | Criar locação |
| GET | `/api/v1/rentals` | Listar locações |
| GET | `/api/v1/rentals/{id}` | Buscar por ID |
| GET | `/api/v1/rentals/user/{userId}` | Locações do usuário |
| PATCH | `/api/v1/rentals/{id}/return` | Devolver carro |
| DELETE | `/api/v1/rentals/{id}` | Cancelar locação |

## Regras de Negócio

- Carro deve estar com status `AVAILABLE` para ser locado
- Cliente não pode ter duas locações `ACTIVE` simultaneamente
- `totalAmount` calculado automaticamente: `dailyRate × número de dias`
- Devolução com atraso gera multa de **20% do dailyRate por dia extra**
- Cancelamento com menos de **24h antes do início** cobra uma diária como multa
- Soft delete em todas as entidades — registros nunca são deletados do banco

## Como Rodar

### Pré-requisitos
- Docker e Docker Compose instalados
- Java 21
- Maven

### 1. Clone o repositório
```bash
git clone https://github.com/seu-usuario/car-rental-api.git
cd car-rental-api
```

### 2. Configure o `.env`
```env
POSTGRES_USER=postgres
POSTGRES_PASSWORD=postgres
POSTGRES_DB=car_rental_db
```

### 3. Suba o banco com Docker
```bash
docker-compose up -d
```

### 4. Rode a aplicação
```bash
mvn spring-boot:run
```

### 5. Acesse o Swagger
```
http://localhost:8080/swagger-ui/index.html
```

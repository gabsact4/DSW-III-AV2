# DSW-III-AV2
sistemas para gestão de oficinas mecânicas e lojas de autopeças.

#  README - Automanager API

##  Sistema de Gerenciamento de Clientes com Spring Boot

Sistema completo para gerenciamento de clientes, incluindo endereços, documentos e telefones, seguindo os níveis 1, 2 e 3 do Richardson Maturity Model (RMM) com HATEOAS.

---

##  Requisitos

| Requisito | Versão |
|-----------|--------|
| **Java** | 25 ou superior |
| **Maven** | 3.9+ |
| **Banco de Dados** | MySQL
| **IDE** | Eclipse / VS Code |

---

##  Tecnologias Utilizadas

- Java 25
- Spring Boot 3.4+
- Spring Data JPA
- Spring HATEOAS
- Spring Validation
- Lombok
- Banco H2 (desenvolvimento)

---

##  Como Executar

### 1. Clone o repositório (ou extraia os arquivos)

```bash
cd automanager
```

### 2. Compile o projeto com Maven

```bash
mvn clean install
```

### 3. Execute a aplicação

```bash
mvn spring-boot:run
```

OU via IDE: Execute a classe `Av1Application.java`

---

##  Exemplos de Requisições

### 1. Cadastrar Cliente Completo

```bash
POST http://localhost:8080/cliente/cadastro
Content-Type: application/json
```

```json
{
  "nome": "Ana Carolina",
  "sobrenome": "Santos",
  "cpf": "12345678900",
  "email": "ana@email.com",
  "telefone": "11999998888",
  "dataNascimento": "1995-03-15T00:00:00.000+00:00",
  "nomeSocial": "Carol",
  "endereco": {
    "estado": "SP",
    "cidade": "São Paulo",
    "bairro": "Vila Mariana",
    "rua": "Rua Domingos de Morais",
    "numero": "2500",
    "codigoPostal": "04035000",
    "informacoesAdicionais": "Apto 101"
  },
  "documentos": [
    {
      "tipo": "RG",
      "numero": "501234567"
    },
    {
      "tipo": "CPF",
      "numero": "12345678900"
    },
    {
      "tipo": "CNH",
      "numero": "SP1234567890"
    }
  ],
  "telefones": [
    {
      "ddd": "11",
      "numero": "99998888"
    },
    {
      "ddd": "11",
      "numero": "33334444"
    }
  ]
}
```

### 2. Listar Todos os Clientes (com HATEOAS)

```bash
GET http://localhost:8080/cliente/todos
```

**Resposta (exemplo):**
```json
{
  "_embedded": {
    "modeloClienteList": [
      {
        "id": 1,
        "nome": "Pedro Alcântara de Bragança e Bourbon",
        "sobrenome": "Bragança",
        "nomeSocial": "Dom Pedro",
        "_links": {
          "self": { "href": "http://localhost:8080/cliente/1" },
          "atualizar": { "href": "http://localhost:8080/cliente/atualizar/1" },
          "excluir": { "href": "http://localhost:8080/cliente/excluir/1" },
          "todos": { "href": "http://localhost:8080/cliente/todos" }
        }
      }
    ]
  },
  "_links": {
    "self": { "href": "http://localhost:8080/cliente/todos" },
    "cadastrar": { "href": "http://localhost:8080/cliente/cadastro" }
  }
}
```

### 3. Buscar Cliente por ID

```bash
GET http://localhost:8080/cliente/1
```

### 4. Atualizar Cliente

```bash
PUT http://localhost:8080/cliente/atualizar/1
Content-Type: application/json
```

```json
{
  "nome": "Pedro II",
  "nomeSocial": "Dom Pedro II",
  "telefone": "21999998888",
  "endereco": {
    "cidade": "Petrópolis",
    "rua": "Rua do Imperador",
    "numero": "220"
  },
  "telefones": [
    {
      "ddd": "24",
      "numero": "977774444"
    }
  ]
}
```

### 5. Excluir Cliente (Remove tudo em cascata)

```bash
DELETE http://localhost:8080/cliente/excluir/1
```

### 6. Buscar Documentos por Tipo

```bash
GET http://localhost:8080/documento/tipo/CPF
```

### 7. Buscar Endereços por Cidade

```bash
GET http://localhost:8080/endereco/cidade/São Paulo
```

### 8. Buscar Telefones por DDD

```bash
GET http://localhost:8080/telefone/ddd/11
```

---


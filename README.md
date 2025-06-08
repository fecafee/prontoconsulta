# 🩺 ProntoConsulta

ProntoConsulta é uma aplicação web para gestão de consultas médicas. O sistema permite cadastrar pacientes e médicos, configurar agendas por turno e horário, marcar consultas, listar agendamentos e realizar cancelamentos.

## 🚀 Tecnologias Utilizadas

### Backend
- Java 21
- Spring Boot 3.5.0
- Spring Data JPA
- Spring Web
- Spring Security (autenticação simples)
- MySQL
- Maven
- Lombok
- DevTools

### Frontend
- HTML5
- CSS3
- JavaScript (puro)

## 🎯 Funcionalidades

- Cadastro de pacientes e médicos
- Cadastro de agendas por médico (com sugestão de horários por turno)
- Marcação de consultas com seleção de horários disponíveis
- Cancelamento de consultas (mantendo histórico)
- Listagem da agenda com nome do paciente, médico, data, hora e status
- Filtro automático de horários indisponíveis
- Validação de horários duplicados

## 📦 Estrutura do Projeto

```
prontoconsulta/
├── backend/
│   ├── controller/
│   ├── entity/
│   ├── repository/
│   ├── service/
│   ├── ProntoconsultaApplication.java
│   └── resources/
│       └── application.properties
├── frontend/
│   ├── index.html
│   ├── style.css
│   └── script.js
```

## 🔧 Como Executar o Projeto

1. Clone o repositório:

```bash
git clone https://github.com/seuusuario/prontoconsulta.git
cd prontoconsulta
```

2. **Configure o banco de dados MySQL:**

```sql
CREATE DATABASE prontoconsulta;
```

3. **Atualize `application.properties` com suas credenciais:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/prontoconsulta
spring.datasource.username=root
spring.datasource.password=senha
```

4. **Rode a aplicação:**

```bash
mvn spring-boot:run
```

5. **Abra `index.html` no navegador para utilizar o sistema.**

## 📋 Endpoints REST Principais

| Recurso     | Verbo | Endpoint                            | Descrição                        |
|-------------|-------|--------------------------------------|----------------------------------|
| Pacientes   | GET   | `/api/pacientes`                    | Lista todos os pacientes         |
| Médicos     | POST  | `/api/medicos`                      | Cadastra um novo médico          |
| Agenda      | GET   | `/api/agendas/medico/{id}`          | Lista agenda de um médico        |
| Consultas   | POST  | `/api/consultas`                    | Marca uma nova consulta          |
| Consultas   | PUT   | `/api/consultas/{id}`               | Atualiza status (ex: cancelar)   |

## 💡 Observações

- O status da consulta pode ser "AGENDADA", "REALIZADA" ou "CANCELADA"
- Ao cancelar uma consulta, o horário volta a ficar disponível na agenda
- Não é possível agendar uma consulta em um horário já utilizado

## 📄 Licença

Este projeto está sob a licença MIT. Sinta-se livre para utilizar, modificar e distribuir.

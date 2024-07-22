# Sistema de Reservas de Hotel
Este projeto é um sistema de reservas de hotel desenvolvido com Spring Boot e PostgreSQL. O sistema permite a gestão de clientes, quartos e reservas. Ele utiliza o AWS Secrets Manager para gerenciar credenciais e configurações sensíveis de banco de dados.

## Tecnologias Utilizadas
* Java 22
* Spring Boot 3.3.2
* PostgreSQL (Aws Rds)
* AWS Secrets Manager
* Configuração do Ambiente
* Clone o Repositório:


## Copiar código

```
git clone https://github.com/usuario/nome-do-repositorio.git
```
cd nome-do-repositorio

## Estrutura do Banco de Dados Tabelas

### Customers
* `id` (PK, int)
* `name` (String)
* `email` (String, único)
* `phone` (String)
* `created_at` (Timestamp)

### Reservations
* `id` (PK, Integer)
* `idCustomer` (int, FK para Customers.id)
* `idRoom` (int, FK para Rooms.id)
* `checkin` (Timestamp)
* `checkout` (Timestamp)
* `status` (enum) com os seguintes valores:
* `SCHEDULED`
* `IN_USE`
* `ABSENCE`
* `FINISHED`
* `CANCELED`

### Rooms
* `id` (PK, int)
* `room_number` (int)
* `type` (String)
* `price` (Double)

## Endpoints da API

## Clientes
* ## GET `/customers` - Listar todos os clientes
* ## POST `/customers` - Criar um novo cliente
* ## PUT `/customers/{id}` - Atualizar um cliente existente
* ## DELETE `/customers/{id}` - Deletar um cliente

# Quartos
* ## GET `/rooms` - Listar todos os quartos
* ## POST `/rooms` - Criar um novo quarto
* ## PUT `/rooms/{id}` - Atualizar um quarto existente
* ## DELETE `/rooms/{id}` - Deletar um quarto

## Reservas
* ## GET `/reservations` - Listar todas as reservas
* ## POST `/reservations` - Criar uma nova reserva
* ## PUT `/reservations/{id}` - Atualizar uma reserva existente
* ## DELETE `/reservations/{id}` - Deletar uma reserva

## Testes
Para testar a aplicação, utilize ferramentas como Postman ou Insomnia para interagir com os endpoints da API.


## Licença
Este projeto está licenciado sob a MIT License.
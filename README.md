# drone-microservices
## 🔧​ Em Construção 🔧​
O Drone-Microservices é um sistema de microserviços para entregas de pacotes utilizando Drones.<br>
(...)
<br>
Todas essas informações são armazenadas em bancos de dados PostgreSQL, cada microserviço possui o seu banco de dados<br>

## :mag: Tecnologias utilizadas
- Construção da API - [Java](https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html) e [Spring Boot](https://spring.io/projects/spring-boot)<br>
- Banco de dados - [PostgreSQL](https://www.postgresql.org/) <br>
-  ORM - [Hibernate ORM with Panache](https://quarkus.io/guides/hibernate-orm-panache) <br>

## 🕵 Diagrama UML da API <br>

![drones drawio](https://github.com/user-attachments/assets/414ee0c9-b5c4-4bba-843f-050befab75fe)

## 🔎 Documentação da API
<details>
<summary><strong>:page_with_curl: Microserviço Gerenciador de Cadastros  </strong></summary><br/>

- Cadastrar um usuário

```
  POST /register/user
```
:point_right: Não é possível cadastrar CPF e E-mail duplicados.<br>
:point_right: As senhas são salvas no banco de dados criptografadas com o algorítimo "SHA-256".<br>

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `nome` | `string` |   nome do usuário |
| `sobrenome` | `string` |  sobrenome do usuário |
| `cpf` | `string` |   CPF do usuário|
| `email` | `string` |   e-mail do usuário |
| `telefone` | `string` |   telefone do usuário |
| `senha` | `string` |   senha de acesso |

  Corpo da resposta: <br/>
  
  
  ```json
  {
"id": 1,
"nome": "John",
"sobrenome": "Martinez",
"email": "xxxx@gmail.com",
"telefone": "5531987198765",
  }
  ```
:white_check_mark: STATUS 201 CREATED

- Buscar um usuário por CPF

```
  GET /register/user/cpf/:cpf
```
  Corpo da resposta: <br/>
  
  
  ```json
{
	"id": 1,
	"nome": "John",
	"sobrenome": "Martinez",
	"email": "xxxx@gmail.com",
	"enderecos": [
		{
			"id": 1,
			"logradouro": "Andradas",
			"numero": 200,
			"complemento": "apt101",
			"bairro": "Centro",
			"cidade": "Belo Horizonte",
			"estado": "Minas Gerais",
			"cep": "30120-010",
			"latitude": "-19.8244097",
			"longitude": "-43.9788706",
		}
	],
	"telefone": "5531987191234"
}
  ```
:white_check_mark: STATUS 200 OK

- Buscar um usuário por id

```
  GET /register/user/id/:id
```
  Corpo da resposta: <br/>
  
  
  ```json
{
	"id": 1,
	"nome": "John",
	"sobrenome": "Martinez",
	"email": "xxxx@gmail.com",
	"enderecos": [
		{
			"id": 1,
			"logradouro": "Andradas",
			"numero": 200,
			"complemento": "apt101",
			"bairro": "Centro",
			"cidade": "Belo Horizonte",
			"estado": "Minas Gerais",
			"cep": "30120-010",
			"latitude": "-19.8244097",
			"longitude": "-43.9788706",
		}
	],
	"telefone": "5531987191234"
}
  ```
:white_check_mark: STATUS 200 OK

- Edição dos dados cadastrais de um usuário

```
 PUT /register/user/id/:id
```
:point_right: Não é permitido editar o CPF.

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `nome` | `string` |   nome do usuário |
| `sobrenome` | `string` |  sobrenome do usuário |
| `cpf` | `string` |   CPF do usuário|
| `email` | `string` |   e-mail do usuário |
| `telefone` | `string` |   telefone do usuário |
| `senha` | `string` |   senha de acesso |

  Corpo da resposta: <br/>
  
  
  ```json
  {
"id": 1,
"nome": "John",
"sobrenome": "Martinez",
"email": "xxxx@gmail.com",
"telefone": "5531987198765",
  }
  ```
:white_check_mark: STATUS 200 OK

- cadastro de um endereço

```
 POST /register/address
```
:point_right: Não é possível cadastrar um endereço para um usuário inexistente.<br>
:point_right: Ao cadastrar um novo endereço uma mensagem é enviada a uma fila que irá proceder com uma requisição ao serviço distancematrix.ai para burcar os valores de latitude e longitude do endereço cadastrado e completar as informações no banco de dados.

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `usuarioId` | `long` |   id do usuário |
| `logradouro` | `string` | logradouro do usuário |
| `numero` | `long` |   numero do logradouro |
| `complemento` | `string` |  complemento do logradouro |
| `bairro` | `string` |   bairro do logradouro |
| `cidade` | `string` |   cidade do usuário |
| `estado` | `string` |   estado a qual a cidade pertence|
| `cep` | `string` |   CEP do logradouro|

  Corpo da resposta: <br/>
  
  
  ```json
{
    "id": 1,
    "logradouro": "Afonso Pena",
    "numero": 84,
    "complemento": "apt202",
    "bairro": "Centro",
    "cidade": "Belo Horizonte",
    "estado": "Minas Gerais",
    "cep": "30130002",
    "latitude": null,
    "longitude": null,
}
  ```
:white_check_mark: STATUS 201 CREATED

- Busca os endereços de usuário pelo seu id

```
GET /register/address/user/:id
```
  Corpo da resposta: <br/>
  
  
  ```json
[
	{
		"id": 1,
		"logradouro": "Andradas",
		"numero": 200,
		"complemento": "apt101",
		"bairro": "Centro",
		"cidade": "Belo Horizonte",
		"estado": "Minas Gerais",
		"cep": "30120-010",
		"latitude": "-19.8244097",
		"longitude": "-43.9788706",
	}
]
  ```
:white_check_mark: STATUS 200 OK

- Busca um endereço pelo seu id

```
GET /register/address/:id
```
  Corpo da resposta: <br/>
  
  
  ```json
	{
		"id": 1,
		"logradouro": "Andradas",
		"numero": 200,
		"complemento": "apt101",
		"bairro": "Centro",
		"cidade": "Belo Horizonte",
		"estado": "Minas Gerais",
		"cep": "30120-010",
		"latitude": "-19.8244097",
		"longitude": "-43.9788706",
	}
  ```
:white_check_mark: STATUS 200 OK

- Deleta um endereço pelo seu id

```
DELETE /register/address/:id
```
  Corpo da resposta: <br/>
  
  
  ```json
Endereço deletado com sucesso
  ```
:white_check_mark: STATUS 200 OK

- Edita um endereço pelo seu  id

```
PUT /register/address/:id
```
:point_right: Ao cadastrar um novo endereço uma mensagem é enviada a uma fila que irá proceder com uma requisição ao serviço distancematrix.ai para burcar os valores de latitude e longitude do endereço cadastrado e completar as informações no banco de dados.

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `logradouro` | `string` | logradouro do usuário |
| `numero` | `long` |   numero do logradouro |
| `complemento` | `string` |  complemento do logradouro |
| `bairro` | `string` |   bairro do logradouro |
| `cidade` | `string` |   cidade do usuário |
| `estado` | `string` |   estado a qual a cidade pertence|
| `cep` | `string` |   CEP do logradouro|

  Corpo da resposta: <br/>
  
  
  ```json
{
    "id": 1,
    "logradouro": "Afonso Pena",
    "numero": 84,
    "complemento": "apt202",
    "bairro": "Centro",
    "cidade": "Belo Horizonte",
    "estado": "Minas Gerais",
    "cep": "30130002",
    "latitude": "-19.9650549",
    "longitude": "-43.803484",
}
  ```
:white_check_mark: STATUS 200 OK

- Cadastro um Drone

```
POST /register/drone
```
:point_right: Não é possível cadastrar um drone com um status inexistente. <br>
:point_right: Ao cadastrar um novo drone com status Disponível é enviado uma mensagem para uma fila que irá verificar se há algum pedido com pendencia de alocação de um drone para realizar a entrega.
  
| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `modelo` | `string` | modelo do drone |
| `marca` | `string` |  marca do drone  |
| `ano` | `string` |  ano de fabricação do drone |
| `status` | `string` |   enun dos status possíveis para o drone|

  Corpo da resposta: <br/>
  
  
  ```json
{
"id": 1,
"modelo": "x",
"marca": "DJI",
"ano": "2018",
"status": "DISPONIVEL"
}
  ```
:white_check_mark: STATUS 201 CREATED

- Busca um drone pelo id

```
GET /register/drone/:id
```

  Corpo da resposta: <br/>
  
  
  ```json
{
"id": 1,
"modelo": "x",
"marca": "DJI",
"ano": "2018",
"status": "DISPONIVEL"
}
  ```
:white_check_mark: STATUS 200 ok

- Edita o status de um drone

```
PUT /register/drone/:id/status/:status
```
:point_right: Não é possível editar um drone com status diferente dos pré-estabelecidos.<br>
:point_right: Ao editar um drone para o status Disponível é enviado uma mensagem para uma fila que irá verificar se há algum pedido com pendencia de alocação de um drone para realizar a entrega.


  Corpo da resposta: <br/>
  
  
  ```json
{
"id": 1,
"modelo": "x",
"marca": "DJI",
"ano": "2018",
"status": "EM_ROTA"
}
  ```
:white_check_mark: STATUS 200 OK

- Busca todos os drones cadastrados

```
GET /register/drone
```

  Corpo da resposta: <br/>
  
  
  ```json
[
	{
		"id": 1,
		"modelo": "x",
		"marca": "DJI",
		"ano": "2018",
		"status": "EM_ROTA"
	},
	{
		"id": 2,
		"modelo": " z908",
		"marca": "xiaomi",
		"ano": "2024",
		"status": "DISPONIVEL"
	}
]
  ```
:white_check_mark: STATUS 200 OK

- Busca drones por tipo de status

```
GET /register/drone/status/:status
```
:point_right: Não é possível buscar um drone com status diferente dos pré-estabelecidos.

  Corpo da resposta: <br/>
  
  
  ```json
[
	{
		"id": 1,
		"modelo": "x",
		"marca": "DJI",
		"ano": "2018",
		"status": "EM_ROTA"
	},
	{
		"id": 2,
		"modelo": " z908",
		"marca": "xiaomi",
		"ano": "2024",
		"status": "EM_ROTA"
	}
]
  ```
:white_check_mark: STATUS 200 OK

</details>
<details>
<summary><strong>:x: Casos de Falhas do Microserviço Gerenciador de Cadastros </strong></summary><br/>

- Ao tentar cadastrar um usuário com CPF e e-mail já existentes na base de dados deve  emitir a exceção `UsuarioExistenteException`<br><br>
:warning: STATUS 409 - CONFLICT
 ```json
	CPF ou Email já cadastrado
  ```
- Ao buscar por um usuário inexistente deve emitir a exceção `UsuarioNaoExistenteException`<br><br>
:warning: STATUS 404 - NOT FOUND
 ```json
	Usuário não encontrado
  ```
- Ao tentar edita o CPF de um usuário deve  emitir a exceção `EdicaoNaoPermitidaException`<br><br>
:x: STATUS 403 - FORBIDDEN
 ```json
	Edição não permitida
  ```
- Ao buscar por um endereço inexistente deve emitir a exceção `EnderecoNaoExistenteException`<br><br>
:warning: STATUS 404 - NOT FOUND
 ```json
	Endereço não encontrado
  ```
- Ao tentar cadastrar, editar ou buscar por um drone com um status diferente dos pré-estabelecidos deve  emitir a exceção `StatusInvalidoExceptionn`<br><br>
:x: STATUS 403 - FORBIDDEN
 ```json
	Status inexistente
 ```

- Ao buscar por um drone inexistente deve emitir a exceção `DroneNaoExistenteException`<br><br>
:warning: STATUS 404 - NOT FOUND
 ```json
	Drone não encontrado
  ```
</details>

<details>
<summary><strong>:gift: Microserviço Gerenciador de Pedidos  </strong></summary><br/>
	
- Cadastro de um Pedido

```
POST /order
```
:point_right: Ao cadastrar um novo pedido é verificado de forma sincrona se o remetente, destinatário e o endereço estão cadastrado no banco de dados.<br>
:point_right: Ao cadastrar o pedido é enviado uma mensagem para uma fila que irá buscar um drone com status disponível para realizar a entrega.<br>
:point_right:Ao cadastrar um pedido o remetente é o destinatário são notificados via SMS. <br>
:point_right: Não é possível cadastrar um pedido quando o microserviço gerenciador de cadastros está indisponível.<br>
  
| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `dataPedido` | `LocalDateTime` | data do pedido |
| `status:` | `string` |  "criado"  |
| `remetenteId` | `long` |  id de identificação do remetente |
| `destinatarioId` | `long` |   id de identificação do destinatário |
| `enderecoId` | `long` |   id de identificação do endereço de entrega |

  Corpo da resposta: <br/>
  
  ```json
  {
	"id": 1,
	"dataPedido": "2024-12-03T10:15:30",
	"dataEntrega": null,
	"status": "CRIADO",
	"endereco": {
		"logradouro": "Afonso Pena",
		"numero": 84,
		"complemento": "apt202",
		"bairro": "Centro",
		"cidade": "Belo Horizonte",
		"estado": "Minas Gerais",
		"cep": "30130002"
	},
	"remetente": {
		"id": 1,
		"nome": "Joe",
		"sobrenome": "Batista",
		"email": "je@gmail.com",
		"telefone": "5531987191832"
	},
	"destinatario": {
		"id": 1,
		"nome": "Joe",
		"sobrenome": "Batista",
		"email": "je@gmail.com",
		"telefone": "5531987191832"
	},
	"droneId": null
}

  ```
:white_check_mark: STATUS 201 CREATED

- Busca um pedido pelo id

```
GET /order/:id
```
:point_right: Não é possível buscar um pedido quando o microserviço gerenciador de cadastros está indisponível.<br>

Corpo da resposta: <br/>

  ```json
  {
	"id": 1,
	"dataPedido": "2024-12-03T10:15:30",
	"dataEntrega": null,
	"status": "CRIADO",
	"endereco": {
		"logradouro": "Afonso Pena",
		"numero": 84,
		"complemento": "apt202",
		"bairro": "Centro",
		"cidade": "Belo Horizonte",
		"estado": "Minas Gerais",
		"cep": "30130002"
	},
	"remetente": {
		"id": 1,
		"nome": "Joe",
		"sobrenome": "Batista",
		"email": "je@gmail.com",
		"telefone": "5531987191832"
	},
	"destinatario": {
		"id": 1,
		"nome": "Joe",
		"sobrenome": "Batista",
		"email": "je@gmail.com",
		"telefone": "5531987191832"
	},
	"droneId": 1
}

  ```
:white_check_mark: STATUS 200 OK

- Busca todos os pedidos de um usuário pelo id do usuário

```
GET /order/user/:id
```
:point_right: Não é possível buscar um pedido pelo usuário id quando o microserviço gerenciador de cadastros está indisponível.<br>

Corpo da resposta: <br/>

  ```json
  
[
	{
	"id": 1,
	"dataPedido": "2007-12-03T10:15:30",
	"dataEntrega": null,
	"status": "CRIADO",
	"enderecoId": 1,
	"remetenteId": 1,
	"destinatarioId": 2,
	"droneId": null
	}
]
  
  ```
:white_check_mark: STATUS 200 OK

- Edita um pedido pelo id

```
PUT /order/:id
```
:point_right: Não é possível editar um pedido com status diferente dos pré-estabelecidos, com remetente, destinatário, endereço e drone não cadastrados e quando o microserviço gerenciador de cadastros está indisponível. Não é possível editar o drone de um pedido quando o seu status é diferente do disponível, ou quando o pedido já está em rota ou finalizado.<br>
:point_right: Não possível editar o status do pedido para "EM_ROTA" pois existe uma endpoint exclusivo para esta alteração.<br>
:point_right: Ao editar o status de um pedido o remetente e destinatário recebem um SMS notificado da ação.<br>

| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `dataPedido` | `LocalDateTime` | data do pedido |
| `dataEntrega` | `LocalDateTime` | data do pedido |
| `status` | `string` |  "criado"  |
| `remetenteId` | `long` |  id de identificação do remetente |
| `destinatarioId` | `long` |   id de identificação do destinatário |
| `enderecoId` | `long` |   id de identificação do endereço de entrega |
| `droneId` | `long` |   id de identificação do drone destinado a realizar a entrega |

  Corpo da resposta: <br/>
  
  ```json
  {
	"id": 1,
	"dataPedido": "2024-12-03T10:15:30",
	"dataEntrega": "2024-12-03T10:15:30",
	"status": "CANCELADO",
	"endereco": {
		"logradouro": "Afonso Pena",
		"numero": 84,
		"complemento": "apt202",
		"bairro": "Centro",
		"cidade": "Belo Horizonte",
		"estado": "Minas Gerais",
		"cep": "30130002"
	},
	"remetente": {
		"id": 1,
		"nome": "Joe",
		"sobrenome": "Batista",
		"email": "je@gmail.com",
		"telefone": "5531987191832"
	},
	"destinatario": {
		"id": 1,
		"nome": "Joe",
		"sobrenome": "Batista",
		"email": "je@gmail.com",
		"telefone": "5531987191832"
	},
	"droneId": 3
}

  ```
:white_check_mark: STATUS 200 OK

- Edita o status do pedido para EM_ROTA

```
PUT /order/new_monitor/:idPedido
```
:point_right: O pedido somente pode ser colocado EM_ROTA caso o seu status esteja como CRIADO.<br>
:point_right: Ao se colocar um pedido EM_ROTA é envidao uma mensagem para uma fila que irá criar o seu primeiro ponto de monitoramento.<br>
:point_right: Ao colocar o pedido EM_ROTA o remetente e destinatário recebem um SMS de notificação.<br>


| Parâmetro   | Tipo       | Descrição                           |
| :---------- | :--------- | :---------------------------------- |
| `latitude` | `string` |   latitude do endereço de onde houve a saída do drone |
| `longitude` | `string` |  longitude do endereço de onde houve a saída do drone  |

 :white_check_mark: STATUS 204 NO CONTENT

</details>

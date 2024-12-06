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

- Cadastro de usuário

```
  POST /register/user
```
OBS: Não é possível cadastrar CPF e E-mail duplicados.

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

- Busca de usuário por CPF

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

- Busca de usuário por id

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
"telefone":
  ```
:white_check_mark: STATUS 200 OK

- Edição os dados cadastrais de um usuário

```
 PUT /register/user/id/:id
```
OBS: Não é permitido editar o CPF.

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
OBS: Não é possível cadastrar um endereço para um usuário inexistente.

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
OBS: Não é possível cadastrar um drone com um status inexistente
  
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
OBS: Não é possível editar um drone com status diferente dos pré-estabelecidos.

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
OBS: Não é possível buscar um drone com status diferente dos pré-estabelecidos.

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
<summary><strong>:x: Casos de Falhas  </strong></summary><br/>

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


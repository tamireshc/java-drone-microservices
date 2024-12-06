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
"telefone":
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

- Edição dos dados cadastrais de um usuário

```
 PUT /register/user/id/:id
```
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

</details>


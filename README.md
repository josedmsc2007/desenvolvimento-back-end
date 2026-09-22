# Contatos API

API REST simples de **cadastro de contatos**, feita com Java + Spring Boot,
com dados guardados em memoria (uma lista Java). Projeto academico.

## Como rodar

Pre-requisitos: Java 17+ e Maven instalados (ou uma IDE como IntelliJ /
Eclipse / VS Code com suporte a Spring Boot, que ja tem Maven embutido).

**Pela linha de comando:**

```
mvn spring-boot:run
```

**Pela IDE:** abra o projeto (pasta `contatos-api`) e rode a classe
`ContatosApiApplication` (metodo `main`).

Quando subir, a API fica disponivel em: `http://localhost:8080`

## Estrutura do projeto

```
src/main/java/com/exemplo/contatosapi/
├── ContatosApiApplication.java     -> classe principal (liga a aplicacao)
├── model/
│   └── Contato.java                -> molde dos dados (id, nome, telefone, email, categoria, favorito)
└── controller/
    └── ContatoController.java      -> rotas da API (GET, POST, PUT, DELETE)
```

Como pedido no trabalho, so existem as camadas **model** e **controller** -
sem service nem repository, e sem banco de dados (os contatos ficam numa
`List<Contato>` dentro do controller e se perdem quando reinicia a
aplicacao).

## Rotas disponiveis

| Metodo | Rota                  | O que faz                          |
|--------|-----------------------|-------------------------------------|
| GET    | /api/contatos          | Lista todos os contatos (com filtros opcionais) |
| GET    | /api/contatos/{id}     | Busca um contato pelo ID            |
| POST   | /api/contatos          | Cadastra um novo contato            |
| PUT    | /api/contatos/{id}     | Atualiza um contato existente       |
| DELETE | /api/contatos/{id}     | Exclui um contato                   |

### Filtros (combinaveis) no GET /api/contatos

- `nome` -> busca por nomes que contenham o texto (ex: `?nome=an`)
- `categoria` -> busca por categoria exata (ex: `?categoria=Trabalho`)
- `favorito` -> `true` ou `false` (ex: `?favorito=true`)

Podem ser usados juntos, por exemplo:

```
GET /api/contatos?categoria=Trabalho&favorito=false
GET /api/contatos?nome=an&favorito=true
```

## Exemplo de contato (JSON)

```json
{
  "id": 1,
  "nome": "Ana Silva",
  "telefone": "11 99999-0001",
  "email": "ana@email.com",
  "categoria": "Familia",
  "favorito": true
}
```

O campo `id` **nao precisa ser enviado** no POST - ele e gerado
automaticamente pelo servidor.

## Testando no Postman

1. Suba a aplicacao (`mvn spring-boot:run`).
2. Abra o Postman e crie uma nova aba.

**1) Listar todos:**
- Metodo: `GET`
- URL: `http://localhost:8080/api/contatos`

**2) Buscar por ID:**
- Metodo: `GET`
- URL: `http://localhost:8080/api/contatos/1`

**3) Cadastrar (POST):**
- Metodo: `POST`
- URL: `http://localhost:8080/api/contatos`
- Aba **Body** -> selecione **raw** -> tipo **JSON** -> cole:
```json
{
  "nome": "Daniela Lima",
  "telefone": "11 98888-0004",
  "email": "dani@email.com",
  "categoria": "Trabalho",
  "favorito": false
}
```
- Resposta esperada: status `201 Created` com o contato criado (ja com id).

**4) Atualizar (PUT):**
- Metodo: `PUT`
- URL: `http://localhost:8080/api/contatos/1`
- Body raw JSON com os dados novos (igual ao POST).
- Resposta esperada: `200 OK` com o contato atualizado.

**5) Excluir (DELETE):**
- Metodo: `DELETE`
- URL: `http://localhost:8080/api/contatos/1`
- Resposta esperada: `204 No Content` (sem corpo).

**6) Filtros combinados:**
- Metodo: `GET`
- URL: `http://localhost:8080/api/contatos?categoria=Trabalho&favorito=false`

## Como explicar cada item pedido no trabalho

- **Rotas**: definidas com `@RequestMapping("/api/contatos")` na classe e
  `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` em cada
  metodo do controller.
- **Metodos HTTP**: GET (ler), POST (criar), PUT (atualizar), DELETE
  (remover) - cada um mapeado para uma acao do CRUD.
- **Envio e recebimento de JSON**: o Spring converte automaticamente objeto
  Java <-> JSON. Quando devolvemos um `Contato` ou uma `List<Contato>`, ele
  vira JSON sozinho; quando recebemos JSON no corpo, ele vira objeto Java.
- **@PathVariable**: usado em `/{id}` para pegar o ID que veio na propria
  URL (GET por id, PUT, DELETE).
- **@RequestParam**: usado nos filtros opcionais do GET (`nome`,
  `categoria`, `favorito`), que podem ser combinados.
- **@RequestBody**: usado no POST e no PUT para pegar o JSON enviado no
  corpo da requisicao e transformar em objeto `Contato`.
- **ResponseEntity**: usado em todos os metodos para controlar o status
  HTTP da resposta (200 OK, 201 Created, 204 No Content, 404 Not Found),
  alem do corpo da resposta.

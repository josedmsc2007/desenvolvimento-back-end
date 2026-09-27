# Contatos API

API REST simples de **cadastro de contatos**, feita com Java + Spring Boot,
com dados guardados em memoria (uma lista Java). Projeto academico.


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


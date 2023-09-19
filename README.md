# Challange API

## Pré-requisitos

- Certifique-se de ter o Docker instalado em sua máquina.

## Configuração inicial

1. Execute o comando abaixo para iniciar um container do Postgres:

   ```bash
   docker run --name challange -e POSTGRES_DB=challangeapi -e POSTGRES_USER=challange -e POSTGRES_PASSWORD=challange -p 5432:5432 -d postgres:latest

A collection do Postman "Challange API.json" pode ser encontrada na raiz do repositório.
## Executando a aplicação
A aplicação será executada na porta 8088.
Documentação da API
A documentação da API com Swagger pode ser acessada no seguinte endereço:

http://localhost:8088/swagger-ui/index.html#

### Endpoints
1. Listar todos os planetas
   Method: GET
    ```bash
    URL: /api/planets

Parameters:

pageable (Query): Parâmetros para paginação.
Response: Lista paginada de planetas.

    
2. Buscar um planeta pelo nome

   Method: GET
    ```bash
    URL: /api/planets/{planet}

Parameters:

planet (Path): Nome do planeta.
Response: Detalhes do planeta buscado.

3. Criar um novo planeta
   Method: POST
    ```bash
    URL: /api/planets

Body:

Detalhes do planeta a ser criado no formato JSON.
Response: Detalhes do planeta criado.

4. Deletar um planeta pelo nome
   Method: DELETE
    
    ```bash
    URL: /api/planets/{planet}

Parameters:

planet (Path): Nome do planeta a ser deletado.
Response: 204 No Content em caso de sucesso.




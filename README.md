### Requisito

É necessário ter o Docker instalado.

### Buildar e rodar o banco

Criar uma rede para os containers:

```
docker network create gestao-rede
```

Buildar:
```
docker build ./banco -t gestao-usuario-banco
```

Rodar:
```
docker run --name gestao-usuario-banco --network gestao-rede -v ./banco/dados:/var/lib/mysql -e MYSQL_DATABASE=gestao-usuario-banco -e MYSQL_ROOT_PASSWORD=senha123 -p 3306:3306 gestao-usuario-banco
```

Antes de rodar criar pasta dados dentro da pasta banco

### Buildar e rodar a aplicação

Buildar:
```
docker build . -t gestao-usuario
```

Rodar:
```
docker run --network gestao-rede -e SPRING_DATASOURCE_URL=jdbc:mysql://gestao-usuario-banco:3306/gestao-usuario-banco -e MYSQL_PASSWORD=senha123 -e JWT_SECRET=16c321a9-3bde-4c06-b63a-229c1b187ba5 -e HASH_PEPPER=shared-secret -p 8080:8080 gestao-usuario
```

A aplicação ficará disponível em `http://localhost:8080`.

`MYSQL_PASSWORD`, `JWT_SECRET` e `HASH_PEPPER` são variáveis de ambiente. Troque os valores de exemplo por valores próprios.

Usuário administrador padrão:

```
E-mail: admin@admin.com
Senha: admin
```

Para essa senha padrão funcionar, use `HASH_PEPPER=shared-secret`.
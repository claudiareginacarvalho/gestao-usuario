### Buildar e rodar o banco

Buildar:
```
docker build ./banco -t gestao-usuario-banco
```

Rodar:
```
docker run -e MYSQL_DATABASE=gestao-usuario-banco -e MYSQL_ROOT_PASSWORD=senha123 -p 3306:3306 gestao-usuario-banco
```

# Enunciat de les fites PJ02-DBAPI #

### Preparació de la base de dades ###
Podeu arrancar una instància de MySQL amb Docker
```bash
docker run --name mysql-hostelhunter -it -e MYSQL_ROOT_PASSWORD=pass -e MYSQL_DATABASE=hostelhunter -e MYSQL_USER=alex -e MYSQL_PASSWORD=tu_contraseña -p 3307:3306 mysql
```

I aturar-la i destuir-la amb
```bash
docker stop mysql-dbapi

docker rm mysql-dbapi
```

En cas de no eliminar-se ho podem forçar amb
```bash
docker rm -f mysql-dbapi
```


### Compilació i funcionament ###

Cal el 'Maven' per compilar el projecte
```bash
mvn clean
mvn compile
mvn test
mvn clean compile test
```

Per executar el projecte a Windows cal
```bash
.\run.ps1 cat.iesesteveterradas.dbapi.AppMain
```

Per executar el projecte a Linux/macOS cal
```bash
./run.sh cat.iesesteveterradas.dbapi.AppMain
```

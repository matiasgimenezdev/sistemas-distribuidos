# Instrucciones de ejecucion
La idea es levantar localmente el contenedor que contiene el servidor, por lo cual, debe tener Docker instalado. Luego, ejecutar el cliente para que se inicie la comunicacion entre procesos.

### Descargar imagen del servidor
```
docker pull mgimenezdev/task-server:v1
```

### Crear el contenedor
```
docker run --name=task-server --network=host -v /var/run/docker.sock:/var/run/docker.sock mgimenezdev/task-server:v1
```
- Puede agregar la opcion **-d o --detach** para ejecutar el servidor en segundo plano. Sin embargo, recomiendo no hacerlo la primera vez para ver el progreso de la ejecucion del servidor.
- La opcion **-v /var/run/docker.sock:/var/run/docker.sock** la usamos para montar el socket de Docker del host en el contenedor, lo que permite que el contenedor pueda interactuar con el demonio de Docker que se está ejecutando en el host.
### Ejecutar el cliente
Abra una terminal nueva o en la misma que el paso anterior, dependiendo de si uso o no la **opcion -d o --detach** en el paso anterior <br>
```
cd client
java -jar target/client.jar
```

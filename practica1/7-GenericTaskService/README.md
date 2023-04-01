### Descargar imagen del servidor
``
docker pull mgimenezdev/task-server:v1
``

### Crear el contenedor (puede agregar la opcion -d si quiere ejecutar el cliente en la misma terminal)
Puede agregar la **opcion -d o --detach** para ejecutar el servidor en segundo plano. Sin embargo, recomiendo no hacerlo la primera vez para ver el progreso de la ejecucion del servidor <br>
``
docker run --name=task-server --network=host -v /var/run/docker.sock:/var/run/docker.sock mgimenezdev/task-server:v1
``

### Ejecutar el cliente (en una nueva terminal o en la misma, dependiendo de si uso o no la opcion --detach en el paso anterior)
``
cd client
java -jar target/client.jar
``

### Descargar imagen del servidor
``
docker pull mgimenezdev/task-server:v1
``

### Crear el contenedor (en caso de no querer ver los logs del contenedor, agregar la opcion -d)
``
docker run --name=task-server --network=host -v /var/run/docker.sock:/var/run/docker.sock task-server
``

### Ejecutar el cliente
``
cd client
java -jar target/client.jar
``

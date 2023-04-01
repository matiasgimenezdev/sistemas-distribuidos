### Descargar imagen del servidor
``
docker pull mgimenezdev/task-server:v1
``

### Crear el contenedor
``
docker run --name=task-server --network=host -v /var/run/docker.sock:/var/run/docker.sock task-server
``

### Ejecutar el cliente
``
cd client
java -jar target/client.jar
``
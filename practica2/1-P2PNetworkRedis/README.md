# P2P Network

## Tecnologias utilizadas 🛠️

-   Java + Spring
-   Docker + Docker compose
-   <a href="https://hub.docker.com/_/nginx">Nginx</a>
-   <a href="https://hub.docker.com/_/redis">Redis</a>
-   <a href="https://github.com/redis/jedis">Jedis: Redis client for Java</a>
-   <a href="https://github.com/matiasgimenezdev/sistemas-distribuidos/blob/main/.github/workflows/P2PNetwork.yml">Github Actions</a>

## Instrucciones de ejecución 📒

Se debe contar con docker y docker-compose instalados.

### 1) Ejecutar el runner.sh

Parado en el directorio raiz de este ejercicio, donde se encuentra el archivo runner.sh, ejecutar:

```
$ bash runner.sh
```

**Este comando iniciara los contenedores con docker compose y registrará a los nodos extremos con los nodos maestros. Cada nodo extremo tiene una serie de archivos de imagen aleatorios que sirven para probar la funcionalidad. Estos se encuentran en /usr/src/files dentro del contenedor.**

### 2) Ver los archivos disponibles en toda la red

Seleccione uno entre los cuatro nodos extremos para probar la funcionalidad de la red. Posteriormente realice una peticion al path /list, reemplazando <PORT> con el numero de puerto asociado al nodo elegido por usted. Los puertos donde escuchan los nodos extremos son desde 9000 al 9003 inclusive.

```
$ curl -X GET http://localhost:<PORT_EXTREMO>/list
```

**Todos los archivos listados estan disponibles para ser descargados.**

### 3) Descargar un archivo desde su nodo elegido

De los archivos listados, seleccione uno para realizar su descarga. Sabiendo cual es su nodo extremo elegido, realice una peticion reemplazando <PORT> y <FILE> con el numero de puerto de su nodo elegido previamente y el nombre de archivo que desea descargar.

```
curl -X GET http://localhost:<PORT>/download\?fileName=<FILE>
```

**Como resultado, vera los archivos que poseía el host antes y despues de la descarga.**

### 4) Detener los contenedores con docker-compose

Parado en el directorio raiz de este ejercicio, donde se encuentra el archivo docker-compose.yml, ejecute el siguiente comando para detener los contenedores y eliminarlos. Tambien eliminara las imagenes docker.

```
$ docker-compose down --rmi all
```

## Diagrama

![Model](https://user-images.githubusercontent.com/117539520/234723125-da59947b-d4e7-4229-b6fd-db04f45c9341.png)

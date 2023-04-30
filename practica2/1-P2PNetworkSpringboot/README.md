# P2P Network

## Instrucciones de ejecución

Se debe contar con docker y docker-compose instalados.

### 1) Iniciar los contenedores con docker-compose

Parado en el directorio raiz de este ejercicio, donde se encuentra el archivo docker-compose.yml,

```
$ docker-compose up -d
```

### 2) Registrar los nodos extremos
Registrar los nodos extremos en los nodos maestros. En nuestro caso, el docker compose esta configurado para levantar 4 nodos extremos y 2 replicas de nodos maestro. Por lo tanto, cada una de las instancias de nodos extremos debe registrarse. Los extremos estan en escucha desde el puerto 9000 al 9003 inclusive. Para realizar el registro puede realizar las peticiones directamnete desde su navegador con el path '/register' o utilizar la herramienta cURL de la siguiente manera.
- En caso de utilizar cURL
```
$ curl -X GET http://localhost:9000/register
$ curl -X GET http://localhost:9001/register
$ curl -X GET http://localhost:9002/register
$ curl -X GET http://localhost:9003/register
```
**Cada nodo extremo tiene una serie de archivos de imagen aleatorios que sirven para probar la funcionalidad. Estos se encuentran en /usr/src/files dentro del contenedor.** 

### 3) Ver los archivos disponibles en toda la red
Seleccione uno entre los cuatro nodos extremos para probar la funcionalidad de la red. Posteriormente realice una peticion al path /list, reemplazando <PORT> con el numero de puerto asociado al nodo elegido por usted.
```
$ curl -X GET http://localhost:<PORT>/list
```
**Todos los archivos listados estan disponibles para ser descargados.**

### 4) Descargar un archivo desde su nodo elegido
De los archivos listados, seleccione uno para realizar su descarga. Sabiendo cual es su nodo extremo elegido, realice una peticion reemplazando <PORT> y <FILE> con el numero de puerto de su nodo elegido previamente y el nombre de archivo que desea descargar.
```
curl -X GET http://localhost:9001/download\?fileName\=file59542.jpg
```
**Como resultado, vera los archivos que poseía el host antes y despues de la descarga.** 
  
### 5) Detener los contenedores con docker-compose

Parado en el directorio raiz de este ejercicio, donde se encuentra el archivo docker-compose.yml, ejecute el siguiente comando para detener los contenedores y eliminarlos. Tambien eliminara las imagenes docker.

```
$ docker-compose down --rmi all
```

## Diagrama

![Model](https://user-images.githubusercontent.com/117539520/234723125-da59947b-d4e7-4229-b6fd-db04f45c9341.png)

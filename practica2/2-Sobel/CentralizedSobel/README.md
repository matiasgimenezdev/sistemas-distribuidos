# Sobel centralizado

## Instrucciones de ejecución 📒

Se debe contar con docker, docker-compose y cURL instalados.

### 1) Iniciar el contenedor con docker-compose

Parado en el directorio raiz de este ejercicio, donde se encuentra el archivo docker-compose.yml,

```
$ docker-compose up -d
```

### 2) Pedir la 'sobelizacion' de una imagen

Parado en el directorio donde se encuentra la imagen a filtrar, utilizar el siguiente comando. Se debe reemplazar <IMAGEN_ORIGINAL> con la ruta a la imagen a la cual se quiere aplicar sobel. Ademas, se debe reemplazar <IMAGEN_FILTRADA> por el nombre que se le quiere dar a la imagen 'sobelizada'. Es necesario que la de la imagen nueva se le asigne la extension '.png'

```
$ curl -X POST -H "Content-Type: image/jpg" --data-binary "@<IMAGEN_ORIGINAL>" http://localhost:8080/filter -o <IMAGEN_FILTRADA.png>
```

## Tiempos de ejecución ⌚

# Instrucciones de ejecucion

###### El proyecto maven ya se encuentra compilado y empaquetado.

1- Abrir dos terminales y moverse en ambas al directorio 'client-server-http-weather': <br>
```
cd client-server-http-weather
```
2- Abrir dos terminales (una para cliente y otra para servidor) y ejecutar en ambas: <br>
```
java -jar target/client-server-http-weather.jar
```
3- En una terminal ingresar la opcion para iniciar el servidor y en la otra terminal ingresar la opcion para iniciar el cliente. <br>

##### Los resultados de la ejecucion se muestran en sus respectivas terminales

Cuando se ejecuta el programa, se solicita al usuario que ingrese el nombre de su 
ciudad. Luego, se construye una URL para hacer una solicitud a la API de 
OpenWeatherMap con la ciudad ingresada y la clave de API proporcionada. 
Se realiza una solicitud GET a la URL y se lee la respuesta JSON devuelta por la API.
Luego, la respuesta se imprime en la consola.

El servidor utiliza la clase HttpServer de Java para crear un servidor HTTP que 
escucha en el puerto 8000. Cuando se recibe una solicitud HTTP en el servidor, se 
llama al método handle del controlador HTTP para procesar la solicitud.

El controlador HTTP obtiene el valor del parámetro "city" de la consulta de la 
solicitud y lo utiliza para construir una URL para hacer una solicitud a la API de 
OpenWeatherMap con la ciudad ingresada y la clave de API proporcionada. Se realiza 
una solicitud GET a la URL y se lee la respuesta JSON devuelta por la API.

La respuesta JSON se envía de vuelta al cliente que realizó la solicitud HTTP en el 
cuerpo de la respuesta HTTP. Si se produce un error al hacer la solicitud a la API 
de OpenWeatherMap, el mensaje de error se envía de vuelta en lugar de la respuesta 
JSON.

Este programa solicita al usuario que ingrese el nombre de su ciudad. Luego, se 
construye una URL para hacer una solicitud GET al servidor HTTP creado en el 
ejemplo anterior con el nombre de la ciudad ingresado como un parámetro de consulta.

Se realiza una solicitud GET a la URL y se lee la respuesta JSON devuelta por el 
servidor. Luego, la respuesta se imprime en la consola junto con el nombre de la 
ciudad.

Es importante tener en cuenta que el servidor HTTP debe estar en ejecución antes de 
que se pueda realizar una solicitud a él. Ejecute el servidor HTTP antes de ejecutar 
este programa.

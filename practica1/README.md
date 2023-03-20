# Practica 1 - SD 2023

## Conceptos básicos para la construcción de Sistemas Distribuidos.

### Ejercicio 1
La clase TCPServer, inicia un servidor TCP abriendo un socket en el puerto 5000 y se pone a la escucha esperando conexiones por parte de algun cliente. El proceso servidor se encuentra bloqueado hasta que llega una conexion TCP la cual aceptará.

Del flujo de entrada de datos (InputStream) de la conexion establecida, lee los bytes que el cliente envia y los convierte en caracteres. Luego, una vez obtenido el mensaje, lo escribe en el flujo de datos de salida (Output Stream).

Una vez hecho esto, cierra el flujo de entrada y salida de datos para liberar recursos. Por ultimo, cierra el socket donde el servidor estaba escuchando y como consecuencia se cierra la conexion con el cliente y se detiene el servidor.

La clase TCPClient, abre una conexion con el servidor TCP. A traves del flujo de salida de datos de la conexion, escribe un mensaje para el servidor. Luego se prepara para recibir la respuesta a traves del flujo de entrada de datos y luego de leer los bytes enviados por el servidor, imprime la respuesta en pantalla.

El cliente tambien cierra los flujos de entrada/salida y cierra la conexion con el servidor.

##### Las instrucciones de ejecucion se encuentran indicadas en cada uno de los proyectos.

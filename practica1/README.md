# Practica 1 - SD 2023

## Conceptos básicos para la construcción de Sistemas Distribuidos.

### Ejercicio 1
La clase "TCPServer", inicia un servidor TCP abriendo un socket en el puerto 5000 y se pone a la escucha esperando conexiones por parte de algun cliente. El proceso servidor se encuentra bloqueado hasta que llega una conexion TCP que es aceptada.

Del flujo de entrada de datos (InputStream) de la conexion establecida, lee los bytes que el cliente envia y los convierte en caracteres. Luego, una vez obtenido el mensaje, lo escribe en el flujo de datos de salida (OutputStream).

Una vez hecho esto, cierra el flujo de entrada y salida de datos para liberar recursos. Por ultimo, cierra el socket donde el servidor estaba escuchando y como consecuencia se cierra la conexion con el cliente y se detiene el servidor.

Por otro lado, la clase "TCPClient", abre una conexion con el servidor TCP escuchando en el puerto 5000. A traves del flujo de salida de datos de la conexion, escribe un mensaje para el servidor. Luego se prepara para recibir la respuesta a traves del flujo de entrada de datos y, una vez leido los bytes enviados por el servidor y convertirlos a caracteres, imprime la respuesta en pantalla. Para finalizar el cliente tambien cierra los flujos de entrada/salida y la conexion con el servidor.

### Ejercicio 2.1
La clase "TCPServerMultithread" inicia un servidor TCP abriendo un socket en el puerto 5000 y se pone en escucha de conexiones entrantes por tiempo indefinido. A diferencia
del servidor el ejercicio 1, este servidor, por cada conexion entrante, crea e inicia un hilo para que se encargue de la conexion con el cliente. Cada nuevo hilo recibe como parametro una instancia de la clase que debe ejecutarse en el.

La clase "RequestHandler" extiende de 'Runnable' e implementa el metodo run(). Esto es requerido para que las instancias se ejecuten en los hilos que el servidor cree. El método
run() implementa la logica para atender las conexiones entrantes. Esta logica implica usar el flujo de entrada de datos para leer el mensaje que llega del cliente, convertir los bytes a caracteres y enviar el mismo mensaje de vuelta al cliente a traves del flujo de datos de salida de la conexion establecida. Finalmente, cierra los flujos de datos de la conexion y el socket para cerrar la conexion con el cliente.

Por otro lado, la clase "TCPClient" funciona igual que el cliente en el ejercicio 1. La unica diferencia es que este en vez de enviar un mensaje hardcodeado, pide al usuario que ingrese el mensaje a enviar.

### Ejercicio 2.2



##### Las instrucciones de ejecucion se encuentran indicadas en cada uno de los proyectos.

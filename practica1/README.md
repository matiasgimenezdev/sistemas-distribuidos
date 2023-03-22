# Practica 1 - SD 2023

## Conceptos básicos para la construcción de Sistemas Distribuidos.

### Ejercicio 1
La clase "TCPServer", inicia un servidor TCP abriendo un socket TCP en el puerto 5000 y se pone a la escucha esperando conexiones por parte de algun cliente. El proceso servidor se encuentra bloqueado hasta que llega una conexion TCP que es aceptada.

Del flujo de entrada de datos (InputStream) de la conexion establecida, lee los bytes que el cliente envia y los convierte en caracteres. Luego, una vez obtenido el mensaje, lo escribe en el flujo de datos de salida (OutputStream).

Una vez hecho esto, cierra el flujo de entrada y salida de datos para liberar recursos. Por ultimo, cierra el socket donde el servidor estaba escuchando y como consecuencia se cierra la conexion con el cliente y se detiene el servidor.

Por otro lado, la clase "TCPClient", abre una conexion con el servidor TCP escuchando en el puerto 5000. A traves del flujo de salida de datos de la conexion, escribe un mensaje para el servidor. Luego se prepara para recibir la respuesta a traves del flujo de entrada de datos y, una vez leido los bytes enviados por el servidor y convertirlos a caracteres, imprime la respuesta en pantalla. Para finalizar el cliente tambien cierra los flujos de entrada/salida y la conexion con el servidor.

### Ejercicio 2.1
La clase "TCPServerMultithread" inicia un servidor TCP abriendo un socket TCP en el puerto 5000 y se pone en escucha de conexiones entrantes por tiempo indefinido (bucle infinito). A diferencia del servidor el ejercicio 1, este servidor, por cada conexion entrante, crea e inicia un hilo para que se encargue de la conexion con el cliente. Cada nuevo hilo recibe como parametro una instancia de la clase que debe ejecutarse en el. Esto es lo que le permite al servidor atender a mas de un cliente a la vez.

La clase "RequestHandler" extiende de 'Runnable' e implementa el metodo run(). Esto es requerido para que las instancias se ejecuten en los hilos que el servidor cree. El método
run() implementa la logica para atender las conexiones entrantes. Esta logica implica usar el flujo de entrada de datos para leer el mensaje que llega del cliente, convertir los bytes a caracteres y enviar el mismo mensaje de vuelta al cliente a traves del flujo de datos de salida de la conexion establecida. Finalmente, cierra los flujos de datos de la conexion y el socket para cerrar la conexion con el cliente.

Por otro lado, la clase "TCPClient" funciona igual que el cliente en el ejercicio 1. La unica diferencia es que este en vez de enviar un mensaje hardcodeado, pide al usuario que ingrese el mensaje a enviar.

### Ejercicio 2.2
La clase "UDPServerMultithread" inicia un servidor UDP abriendo un socket UDP (para datagramas) y se pone en escucha por tiempo indefinido (bucle infinito). Es exactamente el comportamiento que tenia el servidor TCP, solo que el servidor UDP no espera conexiones, sino que espera datagramas enviados por un cliente. Por cada datagrama que recibe, prepara un buffer para almacenar los datos, pero ademas, crea un hilo y lo inicia para que gestione la respuesta, de forma que puede recibir datagramas de mas de un cliente a la vez.

La clase "RequestHandler", recibe la instancia del socket por donde llego el mensaje para poder tener la informacion necesaria para reenviar el mensaje y tambien posee los datos del mensaje (datagrama) recibido. Dentro del metodo run(), lo que hace es obtener del datagrama recibido toda la informacion necesaria para armar el datagrama que enviara como respuesta al cliente. Es decir, arma un datagrama nuevo con los bytes en crudo del mensaje, la longitud de esos bytes y la direccion IP + puerto del cliente (socket). Por ultimo, envia el datagrama de vuelta al cliente. 

Las diferencias clave con el servidor TCP, es que en este caso no se gestionan conexiones, por ende, no se cierra ningun flujo de entrada/salida de datos (streams) o conexion con el cliente, ya que en nunca fueron abiertos debido a que no se utilizan.

La clase "ClienteUDP" le solicita al usuario que ingrese un mensaje. Una vez obtenido el mensaje, lo convertira en bytes y los usara para armar el datagrama que enviará al servidor. Pero para el datagrama tambien va a requerir otros datos como: la direccion IP destino, puerto donde escucha el servidor y la longitud de los bytes del payload. Con todo esto, genera el datagrama y lo envia. Luego, se pone a la escucha en el socket para recibir el datagrama de respuesta enviado por el servidor. Una vez lo recibe, obtiene los datos del mensaje y lo muestra al usuario.

### Ejercicio 3
La clase "MessageQueueServer" tiene casi el mismo comportamiento que los servidores TCP anteriores. La unica diferencia es que posee una estructura HashMap para almacenar la cola de mensajes para cada usuario.

La clase "RequestHandler", recibe ademas del socket donde se abrio la conexion, una referencia a las colas de mensajes. Esta clase tiene dos posibilidades de comportamiento, dependiendo del comando que reciba por parte del usuario. El comando puede ser: SEND para enviar un mensaje a otro usuario y RECEIVE para obtener todos los mensajes de su cola de mensajes. 

En el caso de SEND, lo que ocurre es que queda registrado un mensaje en la cola de mensajes del destinatario. Tanto el nombre del destinatario como el mensaje son enviados por el cliente.

En el caso del RECEIVE, lo que hace es enviar el numero de mensajes que posee en la cola y luego, uno a uno, envia los mensajes.

La clase "MessageQueueClient" implementa todo el comportamiento que permite al usuario enviar un mensaje o leer los que esten en su cola de mensajes.

### Ejercicio 4
Las clases "MessageQueueServer" y "MessageQueueClient" funcionan igual que en el caso anterior. La diferencia esta en la clase "RequestHandler" ya que ahora, al recibir un ACK por parte del cliente (luego de que el usuario lea sus mensajes), debe limpiar la cola de mensajes. Es decir, el cliente una vez que obtiene los mensajes del usuario, envia simplemente el mensaje "ACK" a traves del flujo de salida de datos de la conexion. Cuando del otro lado se lee el mensaje, se verifica que sea un ACK. En caso de serlo, limpia la cola de mensajes asociada al nombre del usuario.

### Ejercicio 4


##### Las instrucciones de ejecucion se encuentran indicadas en cada uno de los proyectos.

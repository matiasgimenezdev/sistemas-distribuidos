# Practica 1 - SD 2023

## Conceptos básicos para la construcción de Sistemas Distribuidos.

### Ejercicio 7
El informe sobre este ejercicio se encuentra en el documento PDF dentro del [repositorio](https://github.com/matiasgimenezdev/sistemas-distribuidos/tree/main/practica1/7-GenericTaskService)

#### 1) ¿En qué casos sería relevante e indispensable contar con este tipo de servicios?
Los **microservicios** son un enfoque arquitectónico y organizativo para el desarrollo de software donde el software está compuesto por pequeños servicios independientes.

Con las arquitecturas monolíticas, todos los procesos están estrechamente asociados y se ejecutan como un solo servicio. Esto significa que, si un proceso de una aplicación experimenta un pico de demanda, se debe escalartoda la arquitectura. Agregar o mejorar las características de una aplicación monolítica se vuelve más complejo a medida que crece la base de código.
Las arquitecturas monolíticas aumentan el riesgo de la disponibilidad de la aplicación porque muchos procesos dependientes y estrechamente vinculados aumentan el impacto del error de un proceso, ya que si se origina un error en un servicio, puede propagarse el error a los demás.

En cambio con una **arquitectura de microservicios**, una aplicación se crea con componentes independientes que ejecutan cada proceso de la aplicación como un servicio. Cada servicio es un componente en una arquitectura de microservicios que fueron creados para desempeñar una unica funcion y la comunicación entre ellos ocurre a través de APIs bien definidas. Cada uno de ellos se puede desarrollar, operar y escalar sin afectar el funcionamiento de otros servicios. Es decir, permite que cada servicio escale de forma independiente para satisfacer la demanda de la aplicación que respalda.

Además, cada servicio está diseñado para resolver un único problema específico. Si se aportará más código a un servicio a lo largo del tiempo, este se volverá más complejo, por lo cual, se podría dividir en servicios más pequeños y simplificados.

![image](https://user-images.githubusercontent.com/117539520/229898938-c6734ef3-97a2-4f1f-a892-9c07d659301e.png)

Los microservicios son útiles en una variedad de situaciones, generalmente para construir aplicaciones escalables, flexibles y resilientes. En los casos donde pueden ser útiles son:
  A. Si una aplicación necesita escalar horizontalmente para manejar un mayor volumen de tráfico o procesamiento. Cada microservicio puede ser escalado de manera             independiente, lo que permite agregar recursos solo donde se necesitan, en lugar de tener que escalar todo el sistema.
  B. Si se busca facilitar el trabajo en equipo, los microservicios pueden simplificar el mantenimiento de una aplicación al permitir que se realicen actualizaciones y       cambios de forma aislada. Esto significa que los desarrolladores pueden trabajar en actualizaciones y mejoras en partes específicas de la aplicación sin tener         que preocuparse por el mpacto en otras partes del sistema.
  C. Son una buena opción para aplicaciones que se ejecutan en arquitecturas distribuidas, donde diferentes partes de la aplicación pueden ejecutarse en diferentes           ubicaciones físicas o lógicas.
  
#### 2) Más allá de haberse implementado a través de una arquitectura de Servidor HTTP. ¿Qué otra tecnología podría haberse utilizado para ejecutar tareas remotas?
Existen diferentes tecnologías que pueden utilizarse para ejecutar tareas de forma remota. Además de la arquitectura de servidor HTTP, se podría haber utilizado RPC (Remote Procedure Call) o comunicación mediante colas de mensajes.
  - Remote Procedure Call (RPC) es un protocolo que permite a un programa en una computadora, solicitar un servicio de otro programa ubicado en otro proceso dentro de      la red. Se utiliza para llamar afunciones o procedimientos de forma remota, de manera transparente y abstrayéndose de la complejidad de la comunicación en red.        La idea detrás de RPC es que su implementación permite que la llamada a procedimientos en otro proceso remoto, funciona de forma similar a una llamada de              procedimiento local
  - La comunicación mediante cola de mensajes es una técnica para comunicar aplicaciones de forma asíncrona y distribuida a través de una cola de mensajes. En una          arquitectura de cola de mensajes, los mensajes se envían a una cola centralizada, donde se almacenan temporalmente y se procesan en orden de llegada. De esta           manera, los procesos que envían y reciben mensajes se comunican a través de ella.

#### 3) Esta solución, si bien es escalable, presenta una limitante a nivel de “sincronismo” entre las partes. ¿Cómo se le ocurre que podría desacoplar las partes y hacer que la solución sea más escalable?
Cómo bien decimos, se puede implementar la comunicación entre partes utilizando colas de mensajes. En lugar de que los microservicios se comuniquen directamente entre sí a través de HTTP, se puede utilizar una cola de mensajes como intermediario. Esto permite que los procesos se comuniquen de manera asíncrona y reduzcan la dependencia entre ellos.

En nuestra implementación, la comunicación entre procesos es síncrona, ya que el proceso emisor realiza una petición y espera que el proceso receptor lo reciba, lo procese y envíe una respuesta. Recién ahí, sigue con su ejecución. Es decir que las partes están acopladas, ya que el cliente se encuentra conectado con el servidor hasta obtener el resultado.

En cambio, si pensamos una implementación donde la comunicación es asíncrona, los procesos funcionarían de forma independiente, ya que el proceso emisor envía un mensaje y continúa su trabajo sin esperar la respuesta inmediata del proceso receptor. La comunicación asíncrona es útil en sistemas distribuidos donde los procesos pueden estar separados geográficamente y tener diferentes velocidades de procesamiento. Además, la comunicación asíncrona puede mejorar la tolerancia a fallos del
sistema distribuido. En nuestro caso, el proceso cliente depende por completo del proceso servidor para obtener el resultado. Se encuentra bloqueado esperando la respuesta, por lo cual, no puede pedirle a otro proceso que realice esa misma operación como segunda alternativa.

Entonces, la comunicación asíncrona puede mejorar la tolerancia a fallos en el sentido que, un proceso puede enviar un mensaje a una cola de mensajes y, si más de un consumidor está suscrito a esa cola, uno podrá responder en lugar del otro en caso de que ocurran fallas. Esto permite que el proceso emisor no dependa de un único consumidor. 
En resumen, si se requiere reducir el acoplamiento entre procesos y aumentar la tolerancia a fallos, el uso de cola de mensajes para implementar
una comunicación asíncrona, puede ser una buena alternativa al uso de HTTP.

### Ejercicio 6
La clase "Server" es un servidor HTTP que se inicia en el puerto 8080 y se pone a la espera de recibir conexiones de clientes. Ademas, este define una ruta '/sum' para que los clientes puedan realizar una peticion POST con ese path en la URL y, enviando dos vectores enteros, recibiran como respuesta el resultado de la suma de esos dos vectores. Para las peticiones que lleguen y matcheen con la ruta '/sum' el servidor instancia a la clase "RequestHandler" para que se encargue de procesar la peticion.

Esta clase "RequestHandler" se encarga de parsear el cuerpo de la peticion POST, donde vienen los parametros necesarios para realizar la suma de los vectores. Convierte el vector sumado a string, luego a bytes y lo envia a traves del flujo de datos de salida de la conexion. Posteriormente, finaliza la conexion con el cliente.

La clase "Cliente" abre una conexion TCP contra el servidor utilizando la URL 'http://localhost:8080/sum' y arma la peticion, incluyendo headers, method (POST) y el body del mensaje HTTP donde envia los vectores que quiere sumar. Si la peticion tuvo exito (codigo 200), muestra el resultado en la consola.

### Ejercicio 5

Este código simula un servidor web HTTP que se encuentra en San Francisco.

La clase "WeatherServer" inicia un servidor web HTTP, el cual escucha en el puerto 5000 las peticiones del cliente por tiempo indefinido.

La clase "ClientHandler" es la encargada de crear las solicitudes mendiante el protocolo HTTP para luego enviarselas al servidor. Esta clase está utilizando la API OpenWeatherMap para poder acceder a la información del clima solicitada por el cliente.
Se genera una conexión HTTP mediante la cuál va a realizar una solicitud mediante el método GET.
La respuesta recibida será luego enviada al cliente.

La clase "WeatherClient" se conecta al servidor en el puerto 5000, y mediante la clase "ClientHandler" obtiene la información sobre el clima en la localidad donde se encuentra el servido, la cual en este caso es San Francisco.

En el caso de que la respuesta HTTP recibida por el servidor tenga como estado un número distinto a 200 (OK), se le informa el error al cliente. Puede probar esto ingrasando una ciudad inexistente, el ClientHandler no podrá obtener información y recibirá un código de error 404.


### Ejercicio 4
Las clases "MessageQueueServer" y "MessageQueueClient" funcionan igual que en el caso anterior. La diferencia esta en la clase "RequestHandler" ya que ahora, al recibir un ACK por parte del cliente (luego de que el usuario lea sus mensajes), debe limpiar la cola de mensajes. Es decir, el cliente una vez que obtiene los mensajes del usuario, envia simplemente el mensaje "ACK" a traves del flujo de salida de datos de la conexion. Cuando del otro lado se lee el mensaje, se verifica que sea un ACK. En caso de serlo, limpia la cola de mensajes asociada al nombre del usuario.

### Ejercicio 3
La clase "MessageQueueServer" tiene casi el mismo comportamiento que los servidores TCP anteriores. La unica diferencia es que posee una estructura HashMap para almacenar la cola de mensajes para cada usuario.

La clase "RequestHandler", recibe ademas del socket donde se abrio la conexion, una referencia a las colas de mensajes. Esta clase tiene dos posibilidades de comportamiento, dependiendo del comando que reciba por parte del usuario. El comando puede ser: SEND para enviar un mensaje a otro usuario y RECEIVE para obtener todos los mensajes de su cola de mensajes. 

En el caso de SEND, lo que ocurre es que queda registrado un mensaje en la cola de mensajes del destinatario. Tanto el nombre del destinatario como el mensaje son enviados por el cliente.

En el caso del RECEIVE, lo que hace es enviar el numero de mensajes que posee en la cola y luego, uno a uno, envia los mensajes.

La clase "MessageQueueClient" implementa todo el comportamiento que permite al usuario enviar un mensaje o leer los que esten en su cola de mensajes.

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

### Ejercicio 1
La clase "TCPServer", inicia un servidor TCP abriendo un socket TCP en el puerto 5000 y se pone a la escucha esperando conexiones por parte de algun cliente. El proceso servidor se encuentra bloqueado hasta que llega una conexion TCP que es aceptada.

Del flujo de entrada de datos (InputStream) de la conexion establecida, lee los bytes que el cliente envia y los convierte en caracteres. Luego, una vez obtenido el mensaje, lo escribe en el flujo de datos de salida (OutputStream).

Una vez hecho esto, cierra el flujo de entrada y salida de datos para liberar recursos. Por ultimo, cierra el socket donde el servidor estaba escuchando y como consecuencia se cierra la conexion con el cliente y se detiene el servidor.

Por otro lado, la clase "TCPClient", abre una conexion con el servidor TCP escuchando en el puerto 5000. A traves del flujo de salida de datos de la conexion, escribe un mensaje para el servidor. Luego se prepara para recibir la respuesta a traves del flujo de entrada de datos y, una vez leido los bytes enviados por el servidor y convertirlos a caracteres, imprime la respuesta en pantalla. Para finalizar el cliente tambien cierra los flujos de entrada/salida y la conexion con el servidor.

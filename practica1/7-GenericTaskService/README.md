# Instrucciones de ejecucion
La idea es levantar localmente el contenedor que contiene el servidor, por lo cual, debe tener Docker instalado. Luego, ejecutar el cliente para que se inicie la comunicacion entre procesos.

### Descargar imagen del servidor
```
docker pull mgimenezdev/task-server:v1
```

### Crear el contenedor
```
docker run --name=task-server --network=host -v /var/run/docker.sock:/var/run/docker.sock mgimenezdev/task-server:v1
```
- Puede agregar la opcion **-d o --detach** para ejecutar el servidor en segundo plano. Sin embargo, recomiendo no hacerlo la primera vez para ver el progreso de la ejecucion del servidor.
- La opcion **-v /var/run/docker.sock:/var/run/docker.sock** es para montar el socket de Docker del host en el contenedor, lo que permite que el contenedor pueda interactuar con el demonio de Docker que se está ejecutando en el host.
- La opcion **--network=host** es para que el contenedor se ejecute en la red del host y pueda interactuar con el contenedor del servicio que va a ejecutarse dentro del host, como contenedor hermano.

### Ejecutar el cliente
Una vez que el contenedor del servidor se encuentra en ejecucion, en una nueva terminal o en la misma, dependiendo de si uso o no la **opcion -d o --detach** en el paso anterior, ubiquese en el directorio 'client' e inicie el cliente. <br>
```
cd client
java -jar target/client.jar
```

# Respuestas
**1. ¿En qué casos sería relevante e indispensable contar con este tipo de servicios?**
Los **microservicios** son un enfoque arquitectónico y organizativo para el desarrollo de software donde el software está compuesto por pequeños servicios independientes.

Con las <u>arquitecturas monolíticas</u>, todos los procesos están estrechamente asociados y se ejecutan como un solo servicio. Esto significa que, si un proceso de una aplicación experimenta un pico de demanda, se debe escalar toda la arquitectura. Agregar o mejorar las características de una aplicación monolítica se vuelve más complejo a medida que crece la base de código. Las arquitecturas monolíticas aumentan el riesgo de la disponibilidad de la aplicación porque muchos procesos dependientes y estrechamente vinculados aumentan el impacto del error de un proceso, ya que si se origina un error en un servicio, puede propagarse el error a los demas.

En cambio con una <u>arquitectura de microservicios</u>, una aplicación se crea con componentes independientes que ejecutan cada proceso de la aplicación como un servicio. Los servicios se crean para desempeñar una única función. Cada servicio es un componente en una arquitectura de microservicios y la comunicación entre ellos ocurre a traves de APIs bien definidas. Cada uno de ellos se puede desarrollar, operar y escalar sin afectar el funcionamiento de otros servicios. Es decir, permite que cada servicio escale de forma independiente para satisfacer la demanda de la aplicacion que respalda.

Ademas, cada servicio esta diseñado para resolver un unico problema especifico. Si se aportara mas codigo a un servicio a lo largo del tiempo, este se volvera mas complejo, por lo cual, se podria dividir en servicios mas pequeños y simplificados. 

Los microservicios son útiles en una variedad de situaciones, pero los casos donde pueden ser utiles son:
- Si una aplicación necesita escalar horizontalmente para manejar un mayor volumen de tráfico o procesamiento. Cada microservicio puede ser escalado de manera independiente, lo que permite agregar recursos solo donde se necesitan, en lugar de tener que escalar todo el sistema. 
- Los microservicios pueden simplificar el mantenimiento de una aplicación al permitir que se realicen actualizaciones y cambios de forma aislada. Esto significa que los desarrolladores pueden trabajar en actualizaciones y mejoras en partes específicas de la aplicación sin tener que preocuparse por el impacto en otras partes del sistema.
- Los microservicios son una buena opción para aplicaciones que se ejecutan en arquitecturas distribuidas, donde diferentes partes de la aplicación pueden ejecutarse en diferentes ubicaciones físicas o lógicas.

**2. Más allá de haberse implementado a través de una arquitectura de Servidor HTTP. ¿Qué otra tecnología podría haberse utilizado para ejecutar tareas remotas?** 
**3. Esta solución, si bien es escalable, presenta una limitante a nivel de “sincronismo” entre las partes. ¿Cómo se le ocurre que podría desacoplar las partes y hacer que la solución sea más escalable?**

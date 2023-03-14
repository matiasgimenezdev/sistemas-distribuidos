# Instrucciones de ejecucion

###### El proyecto maven ya se encuentra compilado y empaquetado.

1- Abrir una terminal y moverse al directorio 'client-server-message-queue': <br>
```
cd client-server-message-queue
```
2- En esa terminal iniciar el servidor UDP : <br>
```
java -cp target/client-server-message-queue.jar com.example.MessageQueueServer
```
3- Abrir dos o mas terminales nuevas para ejecutar diferentes clientes: <br> 
```
java -cp target/client-server-message-queue.jar com.example.MessageQueueClient
```

###### Los resultados de la ejecucion se muestran en sus respectivas terminales

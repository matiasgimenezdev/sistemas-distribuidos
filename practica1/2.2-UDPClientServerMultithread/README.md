# Instrucciones de ejecucion

###### El proyecto maven ya se encuentra compilado y empaquetado.

1- Abrir una terminal y moverse al directorio 'udp-server-client-multithread': <br>
```
cd udp-server-client-multithread
```
2- En esa terminal iniciar el servidor UDP : <br>
```
java -cp target/udp-server-client-multithread.jar com.example.UDPServerMultithread
```
3- Abrir dos o mas terminales nuevas para ejecutar diferentes clientes TCP: <br> 
```
java -cp target/udp-server-client-multithread.jar com.example.UDPClient
```

###### Los resultados de la ejecucion se muestran en sus respectivas terminales

package com.example;
import java.net.*;
import java.io.*;

// Para que el servidor pueda atender a más de un cliente simultáneamente, necesitamos que maneje las conexiones de forma 
// concurrente. 

// Para esto debe crear un nuevo hilo (thread) para cada nueva conexión de cliente para que su solicitud sea atendida,
// pero el servidor por otro lado pueda atender peticiones de otros clientes.


public class TCPServerMultithread {
    public void run() {
        try {
            // Crea un socket servidor en el puerto 5000
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor iniciado. Esperando conexiones entrantes...");

            while (true) {
                // Espera a que llegue una conexión entrante
                Socket clientSocket = serverSocket.accept();

                System.out.println("Conexión entrante aceptada.");

                // Inicia un nuevo hilo para manejar la conexión del cliente
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();


            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




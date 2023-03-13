package com.example;
import java.net.*;
import java.io.*;

// Este servidor acepta conexiones entrantes en el puerto 5000 y espera a que un cliente se conecte. 
// Una vez que se establece la conexión, el servidor obtiene un flujo de entrada para leer los datos enviados por el cliente y 
// un flujo de salida para enviar datos al cliente. Luego, lee el mensaje del cliente, lo envía de vuelta al cliente y 
// cierra los flujos de entrada y salida y su propio socket.

public class TCPServer {
    public static void main(String[] args) {
        try {
            // Crea un socket servidor en el puerto 5000
            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println("Esperando conexiones entrantes...");

            // Espera a que llegue una conexión entrante
            Socket clientSocket = serverSocket.accept();

            System.out.println("Conexión entrante aceptada.");

            // Obtiene un flujo de entrada para leer los datos del cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Obtiene un flujo de salida para enviar datos al cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Lee el mensaje del cliente
            String message = in.readLine();

            // Devuelve el mismo mensaje al cliente
            out.println(message);
            System.out.println("Mensaje devuelto a cliente.");

            // Cierra los flujos de entrada y salida y su propio socket.
            in.close();
            out.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
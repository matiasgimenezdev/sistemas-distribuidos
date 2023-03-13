package com.example;
import java.net.*;
import java.io.*;

// Este cliente se conecta al servidor en el puerto 5000, obtiene un flujo de entrada para leer los datos enviados por el servidor y un 
// flujo de salida para enviar datos al servidor. Luego, envía un mensaje al servidor, lee la respuesta del servidor, imprime la respuesta y 
// cierra los flujos de entrada y salida y el socket del cliente.

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Crea un socket cliente y se conecta al servidor en el puerto 5000
            Socket socket = new Socket("localhost", 5000);

            // Obtiene un flujo de entrada para leer los datos del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Obtiene un flujo de salida para enviar datos al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Envía un mensaje al servidor
            out.println("Hola servidor!");
            System.out.println("Mensaje enviado al servidor: Hola servidor!");

            // Lee la respuesta del servidor
            String response = in.readLine();

            // Imprime la respuesta del servidor
            System.out.println("Servidor envio: " + response);

            // Cierra los flujos de entrada y salida y el socket del cliente
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

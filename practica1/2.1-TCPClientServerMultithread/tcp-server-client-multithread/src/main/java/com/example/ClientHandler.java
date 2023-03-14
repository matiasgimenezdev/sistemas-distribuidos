package com.example;
import java.net.*;
import java.io.*;


// La clase ClientHandler representa el hilo que maneja la conexión del cliente. Cada vez que se recibe una nueva conexión, 
// se crea un nuevo objeto ClientHandler y se inicia un nuevo hilo para ejecutar el método run() de esa clase. 
// En el método run(), se maneja la conexión del cliente.

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            // Obtiene un flujo de entrada para leer los datos del cliente
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // Obtiene un flujo de salida para enviar datos al cliente
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Lee el mensaje del cliente
            String message = in.readLine();

            // Devuelve el mismo mensaje al cliente
            out.println("Servidor: " + message);
            System.out.println("Peticion del cliente atendida");
            
            // Cierra los flujos de entrada y salida y el socket del cliente
            in.close();
            out.close();
            clientSocket.close();
            

        } catch (IOException e) {
            System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
        }
    }
}

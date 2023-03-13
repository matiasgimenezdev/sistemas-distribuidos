package com.example;
import java.net.*;
import java.io.*;

// Para que el servidor pueda atender a más de un cliente simultáneamente, necesitamos que maneje las conexiones de forma 
// concurrente. 

// Para esto debe crear un nuevo hilo (thread) para cada nueva conexión de cliente para que su solicitud sea atendida,
// pero el servidor por otro lado pueda atender peticiones de otros clientes.


public class TCPServerMultithread {
    public static void main(String[] args) {
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
                System.out.println("Se abrio un nuevo hilo.");

            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


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

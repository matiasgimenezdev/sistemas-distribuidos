package com.example;
import java.io.*;
import java.net.*;
import java.util.*;

public class MessageQueueServer {
    // HashMap para almacenar las colas de mensajes de cada destinatario
    private static HashMap<String, LinkedList<String>> messageQueues = new HashMap<String, LinkedList<String>>();

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor de cola de mensajes iniciado en el puerto 5000.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado.");

                // Crear un nuevo hilo para manejar las solicitudes del cliente
                Thread thread = new Thread(new ClientHandler(clientSocket, messageQueues));
                thread.start();
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

}

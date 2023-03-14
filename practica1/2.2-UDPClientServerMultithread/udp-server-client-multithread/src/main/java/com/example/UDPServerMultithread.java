package com.example;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

// Este servidor UDP se pone en escucha en el puerto 5000, esperando recibir mensajes del cliente. Por cada mensaje de cliente
// recibido, abre un hilo para manejar responder al cliente.

public class UDPServerMultithread {

    public static void main(String[] args) throws Exception {
        // crea un socket UDP en el puerto 5000
        DatagramSocket serverSocket = new DatagramSocket(5000);

        System.out.println("Servidor UDP iniciado en el puerto 5000...");
        
        while (true) {
            // espera por una solicitud
            byte[] buffer = new byte[1024];
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            serverSocket.receive(request);
            System.out.println("Solicitud recibida desde el cliente");

            // crea un hilo para procesar la solicitud del cliente
            ClientHandler thread = new ClientHandler(serverSocket, request);
            thread.start();
        }
    }
}



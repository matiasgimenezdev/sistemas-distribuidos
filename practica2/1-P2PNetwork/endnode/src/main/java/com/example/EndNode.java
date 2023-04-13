package com.example;    

import java.net.*;
import java.io.*;

public class EndNode {
    
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9000;

    public void start() {
        try {
            // Conectar como cliente al servidor centralizado
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            System.out.println("Conectado al servidor centralizado");
            
            // Realizar consultas al servidor centralizado
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Consulta 1");
            String response = reader.readLine();
            System.out.println("Respuesta del servidor centralizado: " + response);

            writer.println("Consulta 2");
            response = reader.readLine();
            System.out.println("Respuesta del servidor centralizado: " + response);

            // Atender solicitudes de otros nodos extremos
            ServerSocket serverSocket = new ServerSocket(9001);
            System.out.println("Servidor de nodo extremo iniciado");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nodo extremo conectado: " + clientSocket.getInetAddress().getHostName());
                
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                String request = in.readLine();
                System.out.println("Solicitud recibida: " + request);
                // Procesar la solicitud y enviar una respuesta
                String responseMsg = "Respuesta a solicitud de " + clientSocket.getInetAddress().getHostName();
                out.println(responseMsg);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

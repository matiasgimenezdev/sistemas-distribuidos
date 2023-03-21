package com.example;
import java.net.*;
import java.io.*;

public class TCPServer {
    public  void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor TCP iniciado en el puerto 5000.");

            Socket connection = serverSocket.accept();
            System.out.println("Conexión establecida con el cliente.");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);

            String message = in.readLine();
            out.println(message);
            System.out.println("Mensaje devuelto a cliente.");
            
            in.close();
            out.close();
            serverSocket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
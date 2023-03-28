package com.example;
import java.net.*;
import java.io.*;


public class TCPServerMultithread {
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Servidor TCP iniciado en el puerto 5000.");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexión entrante aceptada.");
                
                Thread thread = new Thread(new RequestHandler(clientSocket));
                thread.start();


            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}




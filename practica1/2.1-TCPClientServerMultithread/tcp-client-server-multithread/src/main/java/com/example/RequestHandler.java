package com.example;
import java.net.*;
import java.io.*;

class RequestHandler implements Runnable {
    private Socket clientSocket;

    public RequestHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message = in.readLine();

            out.println(message);
            System.out.println("Mensaje devuelvo al cliente");
            
            in.close();
            out.close();
            clientSocket.close();
            

        } catch (IOException e) {
            System.out.println("Error al manejar la conexión del cliente: " + e.getMessage());
        }
    }
}

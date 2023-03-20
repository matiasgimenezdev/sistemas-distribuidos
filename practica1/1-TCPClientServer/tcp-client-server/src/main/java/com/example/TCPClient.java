package com.example;
import java.net.*;
import java.io.*;


public class TCPClient {
    public void run() {
        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hola servidor!");
            System.out.println("Servidor recibio: Hola servidor!");

            String response = in.readLine();
            System.out.println("Servidor envio: " + response);
            
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
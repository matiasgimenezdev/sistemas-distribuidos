package com.example;
import java.net.*;
import java.io.*;


public class TCPClient {
    public void run() {
        try {
            Socket connection = new Socket("localhost", 5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
            out.println("Hola servidor!");
            System.out.println("Servidor recibio: Hola servidor!");

            String response = in.readLine();
            System.out.println("Servidor envio: " + response);
            
            in.close();
            out.close();
            connection.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
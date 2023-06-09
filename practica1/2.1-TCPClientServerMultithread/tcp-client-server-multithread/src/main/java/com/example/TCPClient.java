package com.example;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class TCPClient {
    public void run() {
        try {
            // Crea un socket cliente y se conecta al servidor en el puerto 5000
            Socket socket = new Socket("localhost", 5000);

            // Obtiene un flujo de entrada para leer los datos del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Obtiene un flujo de salida para enviar datos al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);  // Crear un objeto Scanner
            System.out.print("Ingrese el mensaje que desea enviar al servidor: ");
            String message = scanner.nextLine().trim();
            
            // Envía un mensaje al servidor
            out.println(message);
            System.out.println("Mensaje enviado al servidor: Hola servidor!");
            
            // Lee la respuesta del servidor
            String response = in.readLine();

            // Imprime la respuesta del servidor
            System.out.println("Servidor envio: " + response);

            // Cierra los flujos de entrada y salida y el socket del cliente
            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

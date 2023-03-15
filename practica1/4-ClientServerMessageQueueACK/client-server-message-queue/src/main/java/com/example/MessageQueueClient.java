package com.example;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MessageQueueClient {
    public void run() {
        try {
            Socket socket = new Socket("localhost", 5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            boolean finish = false;
            Scanner scanner = new Scanner(System.in);

            while(!finish) {
                System.out.println("");
                System.out.println("1- Enviar mensaje");
                System.out.println("2- Leer mensajes");
                System.out.println("");
                System.out.print("Ingrese una opcion: ");
                int option = Integer.parseInt(scanner.nextLine().trim());

                switch(option) {
                    case 1:
                        System.out.println("");
                        System.out.print("Ingrese el destinatario del mensaje: ");
                        String recipient = scanner.nextLine();
                        System.out.println("");
                        System.out.print("Ingrese el mensaje: ");
                        String message = scanner.nextLine();
                        // Enviar un mensaje al destinatario
                        out.println("SEND " + recipient + " " + message);
                        break;
                    case 2: {
                        System.out.println("");
                        System.out.print("Ingrese su nombre: ");
                        String name = scanner.nextLine();
                        System.out.println("");
                        out.println("RECEIVE " + name.trim());
                        
                        // Obtener los mensajes dirigidos al cliente actual
                        int numMessages = Integer.parseInt(in.readLine());
                        System.out.println("Tiene " + numMessages + " mensajes nuevos:");
                        System.out.println("");

                        for (int i = 0; i < numMessages; i++) {
                            System.out.println(i + ". " + in.readLine());
                        }
                        break;
                    }
                    default: finish = true;
                }
         }

            socket.close();
        } catch (IOException e) {
            System.out.println("Error al conectar con el servidor: " + e.getMessage());
        }
    }
}

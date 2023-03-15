package com.example;
import java.io.*;
import java.net.*;
import java.util.*;

    // Clase para manejar las solicitudes de un cliente
    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private HashMap<String, LinkedList<String>> messageQueues;

        public ClientHandler(Socket socket, HashMap<String, LinkedList<String>> messageQueues) {
            this.clientSocket = socket;
            this.messageQueues = messageQueues;
        }

        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                while (true) {
                    String request = in.readLine();
                    if (request == null) {
                        break;
                    }
                    String[] parts = request.split(" ");
                    String command = parts[0];
                    String recipient = parts[1];
                    int option = 0;
                    if(command.equals(new String("SEND"))){
                        option = 1;

                    } else if (command.equals(new String("RECEIVE"))){
                        option = 2;
                    }
                    switch (option) {
                        case 1:
                            String message = "";
                            for(int i = 2; i < parts.length; i ++) {
                                message += parts[i] + " ";
                            }
                            sendMessage(recipient, message);
                            break;
                        case 2:
                            List<String> messages = receiveMessages(recipient);
                            out.println(messages.size());
                            for (String msg : messages) {
                                out.println(msg);
                            }
                            break;
                        default:
                            out.println("Comando no reconocido.");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error al manejar la solicitud del cliente: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar el socket del cliente: " + e.getMessage());
                }
            }
        }

        // Agregar un mensaje a la cola del destinatario
        private void sendMessage(String recipient, String message) {
            synchronized (messageQueues) {
                LinkedList<String> queue = messageQueues.get(recipient);
                if (queue == null) {
                    queue = new LinkedList<String>();
                    messageQueues.put(recipient, queue);
                }
                queue.add(message);
                System.out.println("Mensaje registrado para: " + recipient);
            }
        }

        // Obtener todos los mensajes de la cola del destinatario
        private List<String> receiveMessages(String recipient) {
            synchronized (messageQueues) {
                LinkedList<String> queue = messageQueues.get(recipient);
                if (queue == null) {
                    return new ArrayList<String>();
                } else {
                    List<String> messages = new ArrayList<String>(queue);
                    return messages;
                }
            }
        }
    }

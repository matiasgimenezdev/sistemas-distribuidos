package com.example;

import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
  private static final int PORT = 5000;
  private static Map<String, Queue<String>> messageQueues = new HashMap<>();

  public void run() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Servidor en linea");
      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
        new Thread(new ClientHandler(clientSocket)).start();
      }
    } catch (IOException e) {
      System.err.println("Error al iniciar el servidor: " + e.getMessage());
    }
  }

  private static class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader inputReader;
    private PrintWriter outputWriter;
    private String clientName;

    public ClientHandler(Socket socket) {
      this.clientSocket = socket;
    }

    public void run() {
      try {
        inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outputWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        clientName = inputReader.readLine();
        System.out.println("Nuevo cliente conectado: " + clientName);
        synchronized (messageQueues) {
          if (!messageQueues.containsKey(clientName)) {
            messageQueues.put(clientName, new LinkedList<String>());
          }
        }
        String comando;
        comando = inputReader.readLine();
        if (comando.matches("SEND")) {
          String destinatario;
          destinatario = inputReader.readLine();
          synchronized (messageQueues) {
            if (!messageQueues.containsKey(destinatario)) {
              messageQueues.put(destinatario, new LinkedList<String>());
            }
          }
          String mensaje;
          mensaje = inputReader.readLine();
          sendMessage(destinatario, mensaje);
        } else {
          if (comando.matches("RECEIVE")) {
            List<String> messages = receiveMessages(clientName);
            outputWriter.println(messages.size());
            for (String msg : messages) {
              outputWriter.println(msg);
            }
          }
        }
      } catch (

      IOException e) {
        System.err.println("Error al manejar la conexiÃ³n con el cliente " + clientName + ": " + e.getMessage());
      } finally {
        try {
          clientSocket.close();
        } catch (IOException e) {
          System.err.println(
              "Error al cerrar la conexiÃ³n con el cliente " + clientName + ": " + e.getMessage());
        }
        synchronized (messageQueues) {
          messageQueues.remove(clientName);
        }
        System.out.println("Cliente desconectado: " + clientName);
      }
    }

    private void sendMessage(String recipient, String message) {
      synchronized (messageQueues) {
        Queue<String> queue = messageQueues.get(recipient);
        if (queue == null) {
          queue = new LinkedList<String>();
          messageQueues.put(recipient, queue);
        }
        queue.add(message);
        System.out.println("Mensaje registrado para: " + recipient);
      }
    }

    // Obtener todos los mensajes de la cola del destinatario y eliminarlos de la
    // cola
    private List<String> receiveMessages(String recipient) {
      synchronized (messageQueues) {
        Queue<String> queue = messageQueues.get(recipient);
        if (queue == null) {
          return new ArrayList<String>();
        } else {
          List<String> messages = new ArrayList<String>(queue);
          queue.clear();
          return messages;
        }
      }
    }
  }
}

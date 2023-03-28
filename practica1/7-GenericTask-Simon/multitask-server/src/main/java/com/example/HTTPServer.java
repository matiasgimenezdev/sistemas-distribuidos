package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {

  public void run() throws IOException {
    int port = 8080;
    ServerSocket serverSocket = new ServerSocket(port);
    System.out.println("HTTP Server listening on port " + port);

    while (true) {
      Socket clientSocket = serverSocket.accept();
      System.out.println("Connection received from " + clientSocket.getInetAddress());
      HTTPRequestHandler requestHandler = new HTTPRequestHandler(clientSocket);
      requestHandler.start();
    }
  }

  public static void ejecutarTareaRemota() {
    System.out.println("Tarea remota ejecutada en el servidor.");
  }
}
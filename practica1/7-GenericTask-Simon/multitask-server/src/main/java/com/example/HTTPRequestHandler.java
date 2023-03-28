package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import org.json.JSONObject;

public class HTTPRequestHandler extends Thread {

  private final Socket clientSocket;

  public HTTPRequestHandler(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      OutputStream out = clientSocket.getOutputStream();

      String requestLine = in.readLine();
      System.out.println(requestLine);

      String[] requestLineParts = requestLine.split(" ");
      String method = requestLineParts[0];
      String path = requestLineParts[1];

      JSONObject params = new JSONObject();
      if (method.equals("POST")) {
        String line;
        while (!(line = in.readLine()).equals("")) {
          if (line.startsWith("Content-Length: ")) {
            int contentLength = Integer.parseInt(line.substring(16));
            char[] buffer = new char[contentLength];
            in.read(buffer, 0, contentLength);
            params = new JSONObject(new String(buffer));
          }
        }
      } else if (method.equals("GET")) {
        int queryIndex = path.indexOf('?');
        if (queryIndex >= 0) {
          String queryString = path.substring(queryIndex + 1);
          params = new JSONObject(queryString);
        }
      }

      if (path.startsWith("/ejecutarTareaRemota")) {

        // Ejecutar la tarea remota con los parámetros recibidos
        HTTPServer.ejecutarTareaRemota();

        String responseMessage = "HTTP/1.1 200 OK\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n" +
            "<html><body><h1>Tarea remota ejecutada en el servidor.</h1></body></html>";
        out.write(responseMessage.getBytes());
      } else {
        String responseMessage = "HTTP/1.1 404 Not Found\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n" +
            "<html><body><h1>404 Not Found</h1></body></html>";
        out.write(responseMessage.getBytes());
      }

      out.flush();
      clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
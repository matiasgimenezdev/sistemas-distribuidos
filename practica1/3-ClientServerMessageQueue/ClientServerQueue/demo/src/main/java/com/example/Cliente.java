package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 5000;

  public void run() {
    try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        Scanner scanner = new Scanner(System.in)) {

      System.out.println("Connected to the message queue server");
      System.out.println("Bienvenido al servidor de mensajes. Por favor ingrese su nombre:");
      String clientName = scanner.nextLine();
      if (clientName == null) {
        return;
      }
      output.println(clientName);
      String response = input.readLine();
      System.out.println(response);

      while (true) {
        System.out.println("Enter a command (SEND or RECEIVE):");
        String command = scanner.nextLine();
        output.println(command);

        if (command.equalsIgnoreCase("SEND")) {
          System.out.println("Enter the recipient name:");
          String recipient = scanner.nextLine();
          output.println(recipient);

          System.out.println("Enter the message:");
          String message = scanner.nextLine();
          output.println(message);

          response = input.readLine();
          System.out.println(response);
        } else if (command.equalsIgnoreCase("RECEIVE")) {
          response = input.readLine();
          System.out.println(response);

          response = input.readLine();
          System.out.println(response);

          if (response.startsWith("MESSAGES:")) {
            int numMessages = Integer.parseInt(response.split(":")[1]);
            for (int i = 0; i < numMessages; i++) {
              response = input.readLine();
              System.out.println(response);
            }
          }
        } else {
          System.out.println("Invalid command, try again");
        }
      }
    } catch (IOException e) {
      System.err.println("Error: " + e.getMessage());
    }
  }
}

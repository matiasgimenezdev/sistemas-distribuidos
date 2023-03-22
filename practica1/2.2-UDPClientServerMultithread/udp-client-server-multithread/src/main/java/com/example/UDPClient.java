package com.example;
import java.io.*;
import java.net.*;

public class UDPClient {
    public void run() {
        try {
            System.out.print("Ingrese el mensaje que quiere enviar al servidor: ");
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("");

            DatagramSocket clientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] request = new byte[1024];
            String message = userInput.readLine();
            request = message.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(request, request.length, IPAddress, 5000);
            clientSocket.send(sendPacket);
            System.out.println("Mensaje enviado al servidor.");
            System.out.println("");

            byte[] response = new byte[1024];
            DatagramPacket responseDatagram = new DatagramPacket(response, response.length);
            clientSocket.receive(responseDatagram);
            String responseData = new String(responseDatagram.getData());
            System.out.println("El servidor envio: " + responseData);
            clientSocket.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


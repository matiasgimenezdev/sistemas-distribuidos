package com.example;
import java.io.*;
import java.net.*;

// Este cliente UDP envia un datagrama al servidor UDP escuchando en el puerto 5000, con el mensaje ingresado por el usuario.
// Luego, espera la respuesta del servidor y muestra el pantalla el mensaje recibido.
public class UDPClient {
    public static void main(String args[]) throws Exception {
        System.out.println("Ingrese el mensaje que quiere enviar al servidor");
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        
        // arma el datagrama para enviarlo al servidor
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("localhost");
        byte[] request = new byte[1024];
        String message = userInput.readLine();
        request = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(request, request.length, IPAddress, 5000);
        clientSocket.send(sendPacket);

        // recibe la respuesta del servidor
        byte[] response = new byte[1024];
        DatagramPacket responseDatagram = new DatagramPacket(response, response.length);
        clientSocket.receive(responseDatagram);
        String responseData = new String(responseDatagram.getData());
        System.out.println("El servidor envio: " + responseData);
        clientSocket.close();
    }
}


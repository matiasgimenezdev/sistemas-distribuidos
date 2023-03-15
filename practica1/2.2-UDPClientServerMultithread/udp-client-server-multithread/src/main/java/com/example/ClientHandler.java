package com.example;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientHandler extends Thread {
    private DatagramPacket request;
    private DatagramSocket socket;

    public ClientHandler(DatagramSocket socket, DatagramPacket request) {
        this.request = request;
        this.socket = socket;
    }

    public void run() {
        try {
            // obtiene los datos de la solicitud
            String message = new String(request.getData());
            System.out.println("El cliente envio: " + message);

            // procesa la solicitud
            byte[] responseData = message.getBytes();
            int responseSize = responseData.length;
            InetAddress clientAddress = request.getAddress();
            int clientPort = request.getPort();
            DatagramPacket response = new DatagramPacket(responseData, responseSize, clientAddress, clientPort);

            // envía la respuesta al cliente
            socket.send(response);
            System.out.println("Mensaje enviado al cliente ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}

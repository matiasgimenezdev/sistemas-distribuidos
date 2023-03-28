package com.example;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RequestHandler extends Thread {
    private DatagramPacket datagram;
    private DatagramSocket socket;

    public RequestHandler(DatagramSocket socket, DatagramPacket datagram) {
        this.datagram = datagram;
        this.socket = socket;
    }

    public void run() {
        try {
            String message = new String(datagram.getData());
            System.out.println("El cliente envio: " + message);

            byte[] responseData = message.getBytes();
            int responseSize = responseData.length;
            InetAddress clientAddress = datagram.getAddress();
            int clientPort = datagram.getPort();

            DatagramPacket response = new DatagramPacket(responseData, responseSize, clientAddress, clientPort);
            socket.send(response);
            
            System.out.println("Mensaje enviado al cliente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 
}

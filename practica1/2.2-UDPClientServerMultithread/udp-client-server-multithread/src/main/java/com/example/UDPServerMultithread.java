package com.example;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class UDPServerMultithread {

    public void run()  {
        
        try{
            DatagramSocket serverSocket = new DatagramSocket(5000);

            System.out.println("Servidor UDP iniciado en el puerto 5000...");
            
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
                serverSocket.receive(datagram);

                RequestHandler thread = new RequestHandler(serverSocket, datagram);
                thread.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}



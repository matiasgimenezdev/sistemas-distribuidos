package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MasterNode {
    private int port;
    private List<Socket> peerSockets;

    public MasterNode(int port) {
        this.port = port;
        this.peerSockets = new ArrayList<Socket>();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("MasterNode listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New peer connected: " + socket.getInetAddress());
                peerSockets.add(socket);
                handlePeer(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePeer(Socket socket) {
        try {
            // Código para gestionar la entrada y salida de los peers
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MasterNode masterNode = new MasterNode(9001);
        masterNode.start();
    }
}


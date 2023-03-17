package com.example;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class Server {
    public void run() {
        try{
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            System.out.println("Servidor escuchando el puerto 8080");
            server.createContext("/sum", new ClientHandler());
            server.setExecutor(null);
            server.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

   
}

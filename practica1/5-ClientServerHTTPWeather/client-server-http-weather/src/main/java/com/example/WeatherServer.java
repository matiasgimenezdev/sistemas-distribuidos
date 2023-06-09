package com.example;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class WeatherServer {

    public void run() throws Exception {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(5000), 0);
            System.out.println("Servidor escuchando el puerto 5000");
            server.createContext("/", new ClientHandler());
            server.setExecutor(null);
            server.start();
        } catch (Exception e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
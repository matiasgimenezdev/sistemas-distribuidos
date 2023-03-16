package com.example;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

public class WeatherServer {

    public void run() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor escuchando el puerto 8080");
    }
}

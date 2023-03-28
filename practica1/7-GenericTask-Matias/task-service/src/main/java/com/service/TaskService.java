package com.service;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.json.JSONObject;

public class TaskService {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(5000), 0);
            System.out.println("Servidor escuchando en el puerto 8080");
            server.createContext("/service", new ServiceHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static class ServiceHandler implements HttpHandler {
        public void handle(HttpExchange connection) throws IOException {
            String requestMethod = connection.getRequestMethod();
            if (requestMethod.equalsIgnoreCase("POST")) {
                InputStream requestBody = connection.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody));
                String requestBodyAsString = "";
                String line;
                
                while ((line = reader.readLine()) != null) {
                    requestBodyAsString += line;
                }
                JSONObject parameters = new JSONObject(requestBodyAsString);

                Integer result = ejecutarTarea(parameters);
                OutputStream os = connection.getResponseBody();
                os.write(result.toString().getBytes());
                os.close();

            } else {
                connection.sendResponseHeaders(405, -1); 
            }
        }

        private Integer ejecutarTarea(JSONObject parameters) {
            return 10;
        }
    }
}





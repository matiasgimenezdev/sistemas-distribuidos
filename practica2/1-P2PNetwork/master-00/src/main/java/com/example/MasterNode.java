package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterNode {
    
    private int PORT;
    private static Map<String, List<String>> sharedFiles = new HashMap<>();

    public void start(int PORT) throws IOException {

        this.PORT = PORT;

        HttpServer server = HttpServer.create(new InetSocketAddress(this.PORT), 0);
        server.createContext("/share-files", new ShareFilesHandler());
        server.createContext("/list-files", new ListFilesHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + this.PORT);
    }

    static class ShareFilesHandler implements HttpHandler {
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            if (method.equalsIgnoreCase("POST")) {
                StringBuilder requestBody = new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                JSONObject jsonRequest = new JSONObject(requestBody.toString());
                int js = jsonRequest.getInt("file1.txt");
                //List<String> fileList = new ArrayList<>();
                //sharedFiles.put(jsonRequest.getString("file1.txt"), fileList);
                String response = "Files shared successfully";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
                //System.out.println("Received files shared by node " + jsonRequest.getString("nodeId"));
                System.out.println("Received files shared ");
            } else {
                exchange.sendResponseHeaders(405, -1); // method not allowed
            }
        }
    }

    static class ListFilesHandler implements HttpHandler {
        
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String method = exchange.getRequestMethod();
            if (method.equalsIgnoreCase("GET")) {
                String nodeId = exchange.getRequestURI().getQuery();
                List<String> fileList = sharedFiles.get(nodeId);
                String response = fileList == null ? "No files shared by node " + nodeId : String.join(",", fileList);
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1); // method not allowed
            }
        }
    }
}
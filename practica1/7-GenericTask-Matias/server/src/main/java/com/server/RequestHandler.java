package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {

    @Override
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
            System.out.println(parameters.toString());
            /*
             * A partir de aca, ya obtuvo los parametros necesarios desde el cliente.
             */
            
        } else {
            connection.sendResponseHeaders(405, -1); 
        }


        // JSONObject json = new JSONObject();
        // json.put("name", "jon doe");
        // json.put("age", "22");
        // json.put("city", "chicago");
        // JSONObject json2 = new JSONObject(json.toString());
        // System.out.println(json2.get("name"));

    }

}

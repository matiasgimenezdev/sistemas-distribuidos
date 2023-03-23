package com.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;


    public class RequestHandler implements HttpHandler {
        public void handle(HttpExchange he) throws IOException {
            String requestMethod = he.getRequestMethod().trim();
            if (requestMethod.equalsIgnoreCase("POST")) {
                String body = Utils.convertStreamToString(he.getRequestBody());
                int[] vector1 = Utils.parseVector(body, "vector1");
                int[] vector2 = Utils.parseVector(body, "vector2");
                int[] result = sum(vector1, vector2);
                String response = Utils.convertArrayToString(result);
                he.sendResponseHeaders(200, response.length());
                OutputStream os = he.getResponseBody();
                os.write(response.getBytes());
                os.close();
                he.close();
            } else {
                he.sendResponseHeaders(405, -1); 
            }
        }

        private int[] sum(int[] vector1, int[] vector2) {
            if (vector1.length != vector2.length) {
                throw new IllegalArgumentException("Los vectores deben tener la misma longitud");
            }
            int[] resultado = new int[vector1.length];
            for (int i = 0; i < vector1.length; i++) {
                resultado[i] = vector1[i] + vector2[i];
            }
            return resultado;
        }
    }

 
package com.example;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.*;
import java.util.*;

public class MyHandler implements HttpHandler {

    private static final String API_KEY = "ccd5d173a546a4a269abb5da6df49263"; // Clave API OpenWeatherMap
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public void handle(HttpExchange t) throws IOException {
        String response = "";
        String query = t.getRequestURI().getQuery();
        String city = query.substring(5); // Obtener el valor del parámetro "city" de la consulta
        try {
            URL url = new URL(API_URL + "?q=" + city + "&appid=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                response += output;
            }

            connection.disconnect();
        } catch (Exception e) {
            response = e.toString();
        }

        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
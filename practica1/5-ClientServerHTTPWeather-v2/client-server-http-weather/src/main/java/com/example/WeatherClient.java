package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherClient {

    public void run() throws Exception {
        System.out.println("");
        System.out.println("Cargando...");
            try {
                URL url = new URL("http://localhost:8080");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");

                if (connection.getResponseCode() != 200) {
                    throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
                }

                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                String output;

                System.out.println("");
                System.out.println("Aquí está la información del clima de la ubicacion del servidor");
                System.out.println("");

                while ((output = br.readLine()) != null) {
                    System.out.println(output);
                }

                connection.disconnect();
            } catch (Exception e) {
                System.out.println("Error al conectar con el servidor: " + e.getMessage());
            }
    }
}
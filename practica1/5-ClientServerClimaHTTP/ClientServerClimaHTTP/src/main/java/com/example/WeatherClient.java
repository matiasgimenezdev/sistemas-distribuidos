package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherClient {
    public void run() throws Exception {

        Scanner scanner = new Scanner(System.in);
        boolean finish = false;

        while (!finish) {
            System.out.println("");
            System.out.print("Ingresar ciudad: ");
            System.out.println("");
            String city = scanner.nextLine();

            URL url = new URL("http://localhost:8080/?city=" + city);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

            String output;
            System.out.println("");
            System.out.println("Aquí está la información del clima en " + city + ": ");
            System.out.println("");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            connection.disconnect();

            System.out.println("");
            System.out.println("| Gracias por su consulta |");
            System.out.println("");
            System.out.println("1 - Realizar otra Consulta");
            System.out.println("");
            System.out.println("0 - Salir");
            System.out.println("");

            int option = Integer.parseInt(scanner.nextLine().trim());

            switch (option) {
                case 0:
                    finish = true;
                    break;
                default:
                    break;
            }

        }
    }
}

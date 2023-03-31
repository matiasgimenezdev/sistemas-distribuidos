package com.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

import java.util.Scanner;
import java.util.function.IntToDoubleFunction;

public class Client {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/remotetask");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());

            Scanner sc = new Scanner(System.in);

            System.out.println("");
            System.out.println("Se ha conectado al servidor correctamente: ");
            System.out.println("Ingrese un valor mínimo: ");
            Double min = (double) sc.nextInt();
            System.out.println("Ingrese un valor máximo: ");
            Double max = (double) sc.nextInt();
            System.out.println("");

            JSONObject body = new JSONObject();
            body.put("task", "randomNumber");
            body.put("dockerImage", "mgimenezdev/task-service:v1");
            body.put("dockerImageTag", "v1");
            body.put("dockerContainerName", "task-service");
            body.put("port", "5000");
            body.put("min", min);
            body.put("max", max);
            out.write(body.toString());
            out.flush();
            out.close();

            System.out.println("Enviando datos al servidor...");
            System.out.println("");
            System.out.println("Tarea: randomNumber");
            System.out.println("Imagen: mgimenezdev/task-service:v1");
            System.out.println("Tag: v1");
            System.out.println("Nombre de Contenedor: task-service");
            System.out.println("Puerto : 5000");
            System.out.println("Mínimo: " + min);
            System.out.println("Máximo: " + max);
            System.out.println("");
            System.out.println("Resolviendo la tarea...");
            System.out.println("");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();
            JSONObject responseAsJson = new JSONObject(response.toString());
            Double resultado = responseAsJson.getDouble("result");
            System.out.println("Numero aleatorio: " + resultado.toString());
            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
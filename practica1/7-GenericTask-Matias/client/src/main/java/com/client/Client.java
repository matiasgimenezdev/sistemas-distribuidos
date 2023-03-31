package com.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class Client {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/remotetask");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            JSONObject body = new JSONObject();
            body.put("task", "randomNumber");
            body.put("dockerImage", "mgimenezdev/task-service:v1");
            body.put("dockerImageTag", "v1");
            body.put("dockerContainerName", "task-service");
            body.put("port", "5000");
            body.put("min", 10.0);
            body.put("max", 100.0);
            out.write(body.toString());
            out.flush();
            out.close();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
package com.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.BuildImageResultCallback;



import org.json.JSONObject;
import com.google.gson.Gson;

public class Client {
    private static DockerClient dockerClient;
    
    public static void main(String[] args) {
        try {
            
            // buildAndPushDockerImage();
            URL url = new URL("http://localhost:8080/remotetask");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            JSONObject body = new JSONObject();
            body.put("dockerImage", "generic-task");
            body.put("min", 10.0);
            body.put("max", 100.0);
            body.put("genericTask", gson.toJson(new TareaGenerica()));
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
            Double resultado = responseAsJson.getDouble("taskResult");
            System.out.println("Numero aleatorio: " + resultado.toString());
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void buildAndPushDockerImage() {
        dockerClient = DockerClientBuilder.getInstance().build();
        dockerClient.buildImageCmd().withDockerfile(new File("pathToDockerfile")).withTag("generic-task");
    
        //+ push imagen
    }
}
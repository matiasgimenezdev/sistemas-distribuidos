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
import com.github.dockerjava.core.dockerfile.Dockerfile;
import com.github.dockerjava.api.model.Image;


import org.json.JSONObject;
import com.google.gson.Gson;

public class Client {
    private static DockerClient dockerClient;
    
    public static void main(String[] args) {
        try {
            // String dockerImageName = buildAndPushDockerImage();
            // System.out.println(dockerImageName);

            URL url = new URL("http://localhost:8080/remotetask");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            Gson gson = new Gson();

            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            JSONObject body = new JSONObject();
            body.put("dockerImage", "DockerImageName");
            body.put("min", 10);
            body.put("max", 100);
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
            System.out.println(response.toString());
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // private static String buildAndPushDockerImage() {
    //     dockerClient = DockerClientBuilder.getInstance().build();
    //     String image = dockerClient.buildImageCmd()
    //     .withDockerfile(new File("pathToDockerfile"))
    //     .withTag("tag_name")
    //     .exec(new BuildImageResultCallback())
    //     .awaitImageId();
    //     return image;
    // }
}
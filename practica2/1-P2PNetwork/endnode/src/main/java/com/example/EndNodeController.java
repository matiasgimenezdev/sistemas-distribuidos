package com.example;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@SpringBootApplication
@RestController
public class EndNodeController {

    private EndNode endNode;
    @Autowired
    private Environment env;


    @Autowired
    public EndNodeController(EndNode endNode){
        this.endNode = endNode;
    }

    @GetMapping("/register")
    public ResponseEntity<String> register() throws IOException, InterruptedException {
        JSONObject data = new JSONObject();
        data.put("ip", this.env.getProperty("ip"));
        data.put("port", this.env.getProperty("port"));
        //data.put("ip", "localhost");
        //data.put("port", "9000");
        ArrayList<String> filenames = this.endNode.getList();
        data.put("files", filenames);

        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create("http://" + this.env.getProperty("master.ipAddress") + ":" + this.env.getProperty("master.port") + "/register"))
        //.uri(URI.create("http://" + "localhost" + ":" + "8080" + "/register"))
        .header("Content-Type", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
        .build();

        HttpResponse<String> response = null;
        HttpClient client = HttpClient.newHttpClient();
        response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return ResponseEntity.ok(response.body());
        /*JSONObject responseBody = new JSONObject(response.body());
        return ResponseEntity.ok(responseBody.getString("response"));*/
    }

    @GetMapping("/file")
    public String file(@RequestParam String filename) throws InterruptedException {
        String msj = "";
        try {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("http://" + this.env.getProperty("master.ipAddress") + ":" + this.env.getProperty("master.port") + "/file?filename=" + filename, String.class);
            //ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/file?filename=" + filename, String.class);
            String dataString = response.getBody();
            JSONObject data = new JSONObject(dataString);
            msj = this.endNode.getFile(data, filename);
            this.register();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msj;
    }

    @GetMapping("/getFile")
    public ResponseEntity<File> downloadFile(@RequestParam String filename) {
        File file = this.endNode.searchFile(filename);
        return ResponseEntity.ok()
                .body(file);
    }

    @GetMapping("/list")
    public String listFiles() {
        ArrayList<String> files = this.endNode.getList();
        String filenames = "Files: ";
        for(String file:files){
            filenames += file + "; ";
        }
        return filenames;
    }

}
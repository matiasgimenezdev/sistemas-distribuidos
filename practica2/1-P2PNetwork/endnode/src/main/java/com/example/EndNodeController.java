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
    public EndNodeController(EndNode endNode){
        this.endNode = endNode;
    }

    @GetMapping("/register")
    public ResponseEntity<String> register() throws IOException, InterruptedException {
        JSONObject data = new JSONObject();
        data.put("ip", "localhost");
        data.put("port", "5000");
        ArrayList<String> filenames = this.endNode.getList();
        data.put("files", filenames);

        HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create("http://" + "localhost" + ":" + "8080" + "/share-files"))
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

    public void download() throws IOException {
        try {
            JSONObject data = new JSONObject();
            data.put("filename", "file.txt");
            RestTemplate restTemplate = new RestTemplate();
            String fileUrl = "http://" + "localhost" + ":8080/search-file?filename=" + data.getString("filename");
            ResponseEntity<File> response = restTemplate.exchange(fileUrl, HttpMethod.GET, null, File.class);
            File file = response.getBody();
            this.endNode.saveFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @GetMapping("/file")
    public ResponseEntity<File> downloadFile(@RequestParam String filename) {
        File file = this.endNode.searchFile(filename);
        return ResponseEntity.ok()
                .body(file);
    }

}
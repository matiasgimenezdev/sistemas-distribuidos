package com.example;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;

@SpringBootApplication
@RestController
public class EndNode {
    
    private Map<String, File> files;

    public EndNode() throws IOException {

        this.files = new HashMap<>();

        // Agrega files
        addFile("file1.txt");
        addFile("file2.txt");

        this.start();
    }

    public void start() throws IOException {
        
        try {
            JSONObject data = new JSONObject();
            ArrayList<String> filenames = new ArrayList<>();
            data.put("ip", "localhost");
            data.put("port", "5000");
            for (String filename : files.keySet()) {
                filenames.add(filename);
            }
            data.put("files", filenames);
            URL url = new URL("http://" + "localhost" + ":" + "8080" + "/share-files");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Java client");
            connection.getOutputStream().write(data.toString().getBytes());
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed to register files with master");
                return;
            }
            this.search();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void search() throws IOException {
        try {
            JSONObject data = new JSONObject();
            data.put("filename", "file1.txt");
            URL url = new URL("http://" + "localhost" + ":" + "8080" + "/search-file");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Java client");
            connection.getOutputStream().write(data.toString().getBytes());
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed to register files with master");
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @PostMapping("/file")
    public byte[] pedirArchivo(@RequestBody String filename) {
        // Archivo solicitado
        JSONObject data = new JSONObject(filename);
        String file = data.getString(filename);

        // JSON respuesta
        JSONObject response = new JSONObject();
        // Busca archivo en base al nombre
        response.put(file, this.files.get(file));

        return response.toString().getBytes();
    }

    public void addFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            files.put(file.getName(), file);
        }
    }
}
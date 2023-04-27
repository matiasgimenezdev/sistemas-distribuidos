package com.example;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class EndNode {
    
    private Map<String, File> files;

    public EndNode() throws IOException {

        this.files = new HashMap<>();

        // Agrega files
        addFile("file1.txt");
        addFile("file2.txt");
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
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void addFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            files.put(file.getName(), file);
        }
    }
}
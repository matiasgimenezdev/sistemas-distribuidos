package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONObject;

public class EndNode {
    
    private final ArrayList<String> masterAddresses;
    private final ArrayList<Integer> masterPorts;
    private Properties serviceProperties;
    private Map<String, File> files;

    public EndNode(String configFile, int PORT) throws IOException {

        // Initialize master addresses and ports from config file
        this.masterAddresses = new ArrayList<>();
        this.masterPorts = new ArrayList<>();
        this.files = new HashMap<>();

        // Agrega files
        addFile("file1.txt");
        addFile("file2.txt");

        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            masterAddresses.add(parts[0]);
            masterPorts.add(Integer.parseInt(parts[1]));
        }
        reader.close();


        serviceProperties = new Properties();
        serviceProperties.load(new FileReader("server.properties"));
    }

    public void start() throws IOException {
         // Register files with master
        try {
            JSONObject data = new JSONObject();
            for (String filename : files.keySet()) {
                data.put(filename, files.get(filename).length());
            }
            URL url = new URL("http://" + masterAddresses.get(0) + ":" + masterPorts.get(0) + "/share-files");
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
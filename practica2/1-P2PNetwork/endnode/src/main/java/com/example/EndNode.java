package com.example;    

import java.net.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class EndNode {
    
    private static final String SERVER_CONFIG_FILE = "server.properties";
    private final List<String> masterAddresses;
    private final List<Integer> masterPorts;

    public EndNode() throws IOException {
        // Initialize master addresses and ports from config file
        this.masterAddresses = new ArrayList<>();
        this.masterPorts = new ArrayList<>();
        File configFile = new File(SERVER_CONFIG_FILE);
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            masterAddresses.add(parts[0]);
            masterPorts.add(Integer.parseInt(parts[1]));
        }
        reader.close();
    }

    public void start() {
        try {
            
            Socket socket = new Socket(masterAddresses.get(0), masterPorts.get(0));
                
            System.out.println("Conectado al nodo maestro: " + masterAddresses.get(0));
            
            // Realizar consultas al servidor centralizado
        
            // Realizar consultas al servidor centralizado
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Consulta 1");
            String response = reader.readLine();
            System.out.println("Respuesta del servidor centralizado: " + response);

            writer.println("Consulta 2");
            response = reader.readLine();
            System.out.println("Respuesta del servidor centralizado: " + response);


        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

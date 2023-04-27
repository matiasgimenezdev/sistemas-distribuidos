package com.example;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
public class MasterNode {

    private Map<String, ArrayList<String>> archivosPorNodo;

    public MasterNode() {
        this.archivosPorNodo = new HashMap<String, ArrayList<String>>();
    }

    @PostMapping("/")
    public String handleRequest(@RequestBody String requestBody){
        System.out.println("Mensaje recibido: " + requestBody);
        return "Respuesta del Nodo Maestro: " + requestBody;
    }

    @PostMapping("/share-files")
    public ResponseEntity<String> recibirArchivos(@RequestBody String body) {
        ObjectMapper mapper = new ObjectMapper();
        try{
            Map<String, Object> jsonMap = mapper.readValue(body, Map.class);
            ArrayList<String> filenames = (ArrayList<String>) jsonMap.get("files");
            String ip = (String) jsonMap.get("ip");
            String port = (String) jsonMap.get("port");
            String socket = ip + ":" + port;
            archivosPorNodo.put(socket, filenames);
            return ResponseEntity.ok("Nombres de archivos recibidos por el Nodo Maestro");
        } catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/search-file")
    public ResponseEntity<String> pedirArchivo(@RequestBody String filename) throws IOException {
        
        try {
            JSONObject data = new JSONObject(filename);
            String socket = getKeyByValue(this.archivosPorNodo, data.getString("filename"));
            String[] endNode = socket.split(":", 2);
            String ip = endNode[0];
            String port = endNode[1];

            URL url = new URL("http://" + "localhost" + ":" + "5000" + "/file");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "Java client");
            connection.getOutputStream().write(data.toString().getBytes());
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.err.println("Failed to register files with master");
                return null;
            }
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getKeyByValue(Map<String, ArrayList<String>> map, String value) {
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            ArrayList<String> filenames = entry.getValue();
            for(String filename : filenames){
                if (filename.equals(value)) {
                    return entry.getKey();
                }
            }
        }
        return null;
    }

}


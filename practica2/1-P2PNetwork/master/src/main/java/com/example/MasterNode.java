package com.example;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
public class MasterNode {

    private Map<String, ArrayList<String>> archivosPorNodo = new HashMap<String, ArrayList<String>>();

    public MasterNode() {
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
            this.archivosPorNodo.put(socket, filenames);
            System.out.println(".");
            System.out.println(".");
            System.out.println("Endo node registrado: " + socket);
            System.out.println(".");
            return ResponseEntity.ok("Nombres de archivos recibidos por el Nodo Maestro");
        } catch(JSONException e){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} catch (Exception e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/search-file")
    public ResponseEntity<File> downloadFile(@RequestParam String filename) {
            String socket = getKeyByValue(this.archivosPorNodo, filename);
            String[] endNode = socket.split(":", 2);
            String ip = endNode[0];
            String port = endNode[1];
            System.out.println(".");
            System.out.println(".");
            System.out.println("Solicitud de archivo " +  filename + " recibida");
            RestTemplate restTemplate = new RestTemplate();
            System.out.println(".");
            System.out.println(".");
            System.out.println("Solicitando archivo a End Node correspondiente");
            String fileUrl = "http://" + ip +  ":" + port + "/file?filename=" + filename;
            ResponseEntity<File> response = restTemplate.exchange(fileUrl, HttpMethod.GET, null, File.class);
            System.out.println(".");
            System.out.println(".");
            System.out.println("Enviando respuesta");
            return response;
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


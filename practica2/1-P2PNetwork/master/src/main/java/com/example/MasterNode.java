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

import java.net.Socket;
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
    public ResponseEntity<String> recibirNombresArchivos(@RequestBody String body) {
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
}


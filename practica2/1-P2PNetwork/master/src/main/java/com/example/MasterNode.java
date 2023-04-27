package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
public class MasterNode {

    private List<Socket> peerSockets;
    private Map<Socket, List<String>> archivosPorNodo;

    public MasterNode() {
        this.peerSockets = new ArrayList<Socket>();
        this.archivosPorNodo = new HashMap<Socket, List<String>>();
    }

    @PostMapping("/share-files")
    public String handleRequest(@RequestBody String requestBody){
        System.out.println("Mensaje recibido: " + requestBody);
        return "Respuesta del Nodo Maestro: " + requestBody;
    }


}


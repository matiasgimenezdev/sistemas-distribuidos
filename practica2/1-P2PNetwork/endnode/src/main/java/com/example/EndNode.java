package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Env;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EndNode {

    private Map<String, File> files = new HashMap<>();
    private String directoryPath = "src/main/files/";
    
    public EndNode() throws IOException {
        this.createFile();
    }

    public void createFile() throws IOException {
        // Agrega files
        Random r = new Random();
        int n = r.nextInt(100) + 1;
        String fileName = "file" + n + ".txt";
        // Crea una instancia de File para el directorio
        File directory = new File(directoryPath);
        // Crea el directorio si no existe
        if (!directory.exists()) {
            directory.mkdirs();
        }
        // Crea una instancia de FileWriter con la ruta completa del archivo
        FileWriter fileWriter = new FileWriter(directoryPath + fileName);
        // Crea una instancia de BufferedWriter para escribir en el archivo
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        // Escribe una cadena en el archivo
        bufferedWriter.write("Este es un ejemplo de archivo de texto.");
        bufferedWriter.close();
        // Agrega File a Map files
        addFile(directoryPath + fileName);
    }

    public void addFile(String filename){
        File file = new File(filename);
            if (file.exists()) {
                this.files.put(file.getName(), file);
        }
    }

    public File searchFile(String filename){
        File file = files.get(filename);
        if(file.exists()){
            return file;    
        }
        return null;
    }

    public void saveFile(File file) throws IOException{
        File downloadedFile = new File("src/main/files/filename.txt");
        Files.copy(file.toPath(), downloadedFile.toPath());
    }

    public ArrayList<String> getList(){
        File folder = new File(this.directoryPath); // Carpeta a examinar
        File[] fileList = folder.listFiles(); // Obtener lista de archivos

        ArrayList<String> filenames = new ArrayList<>();
        for (File file : fileList) {
            if (file.isFile()) {
                filenames.add(file.getName()); // Agregar nombre de archivo al array
            }
        }
        return filenames;
    }

    public void getFile(JSONObject data, String filename) throws IOException {
        String ip = data.getString("ip");
        String port = data.getString("port");
        RestTemplate restTemplate = new RestTemplate();
        String fileUrl = "http://" + ip + ":" + port + "/getFile?filename=" + filename;
        ResponseEntity<File> response = restTemplate.exchange(fileUrl, HttpMethod.GET, null, File.class);
        File file = response.getBody();
        this.saveFile(file);
    }
}

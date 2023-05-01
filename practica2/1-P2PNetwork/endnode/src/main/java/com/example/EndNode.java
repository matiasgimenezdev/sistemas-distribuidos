package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.JSONObject;

@SpringBootApplication
@RestController
public class EndNode {
    private Map<String, File> files;

    public EndNode() throws IOException {
        this.files = new HashMap<>();
        createFile();
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
            System.out.println(".");
            System.out.println(".");
            System.out.println("Registrando End Node");
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
            System.out.println(".");
            System.out.println("End Node regisrtado exitosamente");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void search() throws IOException {
        try {
            JSONObject data = new JSONObject();
            data.put("filename", "file.txt");
            RestTemplate restTemplate = new RestTemplate();
            System.out.println(".");
            System.out.println(".");
            System.out.println("Buscar archivo file.txt");
            String fileUrl = "http://" + "localhost" + ":8080/search-file?filename=" + data.getString("filename");
            ResponseEntity<File> response = restTemplate.exchange(fileUrl, HttpMethod.GET, null, File.class);
            
            // save the file to the local file system
            File file = response.getBody();
            File downloadedFile = new File("filename");
            Files.copy(file.toPath(), downloadedFile.toPath());
            System.out.println(".");
            System.out.println(".");
            System.out.println("Archivo registrado");

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    @GetMapping("/file")
    public ResponseEntity<File> downloadFile(@RequestParam String filename) {
        
        File file = files.get(filename);
        
        return ResponseEntity.ok()
                .body(file);
    }

    public void createFile() {

        // Agrega files
        System.out.println("Generando archivos");
        // Nombre del archivo que se creará
        String fileName = "file.txt";

        // Ruta del directorio donde se guardará el archivo
        String directoryPath = "src/main/files/";

        // Crea una instancia de File para el directorio
        File directory = new File(directoryPath);

        try {
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

            // Cierra el BufferedWriter
            bufferedWriter.close();

            System.out.println("El archivo se creó correctamente en " + directoryPath);

            File file = new File(directoryPath + fileName);
                if (file.exists()) {
                    this.files.put(file.getName(), file);
            }

            } catch (IOException e) {
                System.out.println("Ocurrió un error al crear el archivo.");
            e.printStackTrace();
        }
    }

}
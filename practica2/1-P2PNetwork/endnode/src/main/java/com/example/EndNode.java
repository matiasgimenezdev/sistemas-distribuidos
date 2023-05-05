package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Component;

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
        File downloadedFile = new File("filename");
        Files.copy(file.toPath(), downloadedFile.toPath());
    }

    public ArrayList<String> getList(){
        ArrayList<String> filenames = new ArrayList<>();
        for (String filename : files.keySet()) {
            filenames.add(filename);
        }
        return filenames;
    }
}

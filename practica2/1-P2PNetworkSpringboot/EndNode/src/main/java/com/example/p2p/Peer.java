package com.example.p2p;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class Peer {

  private List<String> availableFiles = new ArrayList<String>();

  public String[] getAvailableFiles() {
    updateAvailableFiles();
    String[] fileList = new String[availableFiles.size()];
    for (int i = 0; i < fileList.length; i++) {
      fileList[i] = availableFiles.get(i);
      System.out.println(fileList[i]);
    }
    return fileList;
  }

  private void updateAvailableFiles() {
    String DIR = System.getProperty("user.dir");
    System.out.println(DIR);
    File directory = new File(DIR + "/files");
    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isFile()) {
            String fileName = file.getName();
            availableFiles.add(fileName);
          }
        }
      }
    }
  }

  public void saveFile(Resource resource) throws IOException {
    String DIR = System.getProperty("user.dir");
    Path directory = Paths.get(DIR + "/EndNode/filesDownloaded/");
    if (!Files.exists(directory)) {
      Files.createDirectories(directory);
    }
    String fileName = resource.getFilename();

    InputStream inputStream = resource.getInputStream();
    OutputStream outputStream = new FileOutputStream(
      new File(directory.toFile(), fileName)
    );
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
    }

    outputStream.flush();
    outputStream.close();
    inputStream.close();
  }
}

package com.example.p2p;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class Peer {

  private List<String> availableFiles = new ArrayList<String>();

  public String[] getAvailableFiles() {
    updateAvailableFiles();
    String[] fileList = new String[availableFiles.size()];
    for (int i = 0; i < fileList.length; i++) {
      fileList[i] = availableFiles.get(i);
    }
    return fileList;
  }

  private void updateAvailableFiles() {
    availableFiles.clear();
    Path currentPath = Paths.get("");
    String currentDir = currentPath.toAbsolutePath().toString();
    File directory = new File(currentDir + "/files/");
    System.out.println(currentDir + "/files/");
    if (directory.isDirectory()) {
      File[] files = directory.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isFile()) {
            String fileName = file.getName();
            availableFiles.add(fileName);
            System.out.println(fileName);
          }
        }
      }
    }
  }
}

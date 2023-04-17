package com.example.p2p;

import java.io.File;
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
      System.out.println(fileList[i]);
    }
    return fileList;
  }

  private void updateAvailableFiles() {
    String DIR = System.getProperty("user.dir");
    System.out.println(DIR);
    File directory = new File(DIR + "/EndNode/files");
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
}

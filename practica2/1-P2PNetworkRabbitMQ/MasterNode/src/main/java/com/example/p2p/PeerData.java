package com.example.p2p;

import java.util.ArrayList;

public class PeerData {

  private String ipAddress;
  private String port;
  private ArrayList<String> files;

  public PeerData(String ipAddress, String port, String[] endNodeFiles) {
    this.ipAddress = ipAddress;
    this.port = port;
    this.files = new ArrayList<String>();
    this.setFiles(endNodeFiles);
  }

  public String getIpAddress() {
    return this.ipAddress;
  }

  public String getPort() {
    return this.port;
  }

  public String[] getFiles() {
    String[] peerFiles = new String[files.size()];
    for (int i = 0; i < files.size(); i++) {
      peerFiles[i] = files.get(i);
    }
    return peerFiles;
  }

  public void setFiles(String[] endNodeFiles) {
    this.files.clear();
    if (endNodeFiles.length > 0) {
      for (String file : endNodeFiles) {
        this.files.add(file);
      }
    }
  }
}

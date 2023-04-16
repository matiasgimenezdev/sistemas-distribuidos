package com.example.p2p;

import org.json.JSONObject;

public class NetworkService {

  public void register() {
    // TODO Registrarse con el nodo Master
  }

  public void download(JSONObject fileInformation) {
    String file = fileInformation.getString("fileName");
    String peerIpAddres = fileInformation.getString("ipAddress");
    String peerPort = fileInformation.getString("port");
    System.out.println(
      "Downloading " + file + " from " + peerIpAddres + ":" + peerPort
    );
    // TODO Descargar archivo desde otro peer
  }

  public void deliver(String file) {
    // TODO Entregar archivo a otro peer
  }

  public void getFileInformation(String file) {
    // TODO Entregar archivo a otro peer
  }
}

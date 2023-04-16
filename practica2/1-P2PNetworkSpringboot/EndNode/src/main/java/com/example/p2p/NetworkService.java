package com.example.p2p;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class NetworkService {

  private Environment env;

  @Autowired
  public NetworkService(Environment env) {
    this.env = env;
  }

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

  public JSONObject getFileInformation(String file) throws Exception {
    HttpClient client = HttpClient.newHttpClient();
    String masterIpAddress = env.getProperty("master.ipAddress").trim();
    String masterPort = env.getProperty("master.port").trim();
    String url =
      "http://" +
      masterIpAddress +
      ":" +
      masterPort +
      "/getFile?file=" +
      file.trim();

    HttpRequest request = HttpRequest
      .newBuilder()
      .uri(URI.create(url))
      .GET()
      .build();

    HttpResponse<String> response = null;
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject responseBody = new JSONObject(response.body());
    JSONObject fileInformation = new JSONObject(
      responseBody.getString("response")
    );
    return fileInformation;
  }
}

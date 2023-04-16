package com.example.p2p;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class NetworkService {

  private Environment env;
  private HttpClient client;

  @Autowired
  public NetworkService(Environment env) {
    this.env = env;
    this.client = HttpClient.newHttpClient();
  }

  public JSONObject register(String[] availableFiles) throws Exception {
    JSONObject registerInformation = new JSONObject();
    registerInformation.put("ipAddress", env.getProperty("peer.ipAddress"));
    registerInformation.put("port", env.getProperty("peer.port"));
    registerInformation.put("files", availableFiles);

    String masterIpAddress = env.getProperty("master.ipAddress").trim();
    String masterPort = env.getProperty("master.port").trim();
    String url = "http://" + masterIpAddress + ":" + masterPort + "/register";

    HttpRequest request = HttpRequest
      .newBuilder()
      .uri(URI.create(url))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(registerInformation.toString()))
      .build();

    HttpResponse<String> response = null;
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject responseBody = new JSONObject(response.body());
    return responseBody;
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

  public Resource deliver(String file) throws FileNotFoundException {
    String DIR = System.getProperty("user.dir");
    Resource resource = new FileSystemResource(DIR + "/" + file);
    return resource;
  }

  public JSONObject getFileInformation(String file) throws Exception {
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

  private void post() {
    // TODO : Implementar peticiones POST
  }

  private void get() {
    // TODO : Implementar peticiones GET
  }
}

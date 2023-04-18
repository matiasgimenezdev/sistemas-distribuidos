package com.example.p2p;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
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

    JSONObject response = post(url, registerInformation);
    return response;
  }

  public void download(JSONObject fileInformation) throws Exception {
    String fileName = fileInformation.getString("fileName");
    String peerIpAddress = fileInformation.getString("ipAddress");
    String peerPort = fileInformation.getString("port");
    System.out.println(
      "Downloading " +
      fileName +
      " from " +
      peerIpAddress +
      ":" +
      peerPort +
      "..."
    );

    String url =
      "http://" +
      peerIpAddress +
      ":" +
      peerPort +
      "/file?file=" +
      fileName.trim();
    //TODO Implementar solicitud de descarga de archivo a otro peer
  }

  public Resource deliver(String fileName) throws FileNotFoundException {
    String DIR = System.getProperty("user.dir");
    Resource resource = new FileSystemResource(
      DIR + "/EndNode/files/" + fileName
    );
    System.out.println(resource.getFilename());
    System.out.println("Entregando: " + DIR + "/EndNode/files/" + fileName);
    // Hasta acá lo hace bien, porque resource.getFilename()) devuelve el nombre del archivo
    // que se tiene que enviar en la respuesta
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

    JSONObject response = get(url);
    return response;
  }

  private JSONObject post(String url, JSONObject body) throws Exception {
    HttpRequest request = HttpRequest
      .newBuilder()
      .uri(URI.create(url))
      .header("Content-Type", "application/json")
      .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
      .build();

    HttpResponse<String> response = null;
    response = client.send(request, HttpResponse.BodyHandlers.ofString());
    JSONObject responseBody = new JSONObject(response.body());
    return responseBody;
  }

  private JSONObject get(String url) throws Exception {
    HttpRequest request = HttpRequest
      .newBuilder()
      .uri(URI.create(url))
      .GET()
      .build();
    HttpResponse<String> response = null;
    response = client.send(request, HttpResponse.BodyHandlers.ofString());

    JSONObject responseBody = new JSONObject(response.body());
    JSONObject responseData = new JSONObject(
      responseBody.getString("response")
    );
    return responseData;
  }
}

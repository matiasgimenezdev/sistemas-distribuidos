package com.example.p2p;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Random;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

  public JSONObject getFileInformation(String file) throws Exception {
    String masterIpAddress = env.getProperty("master.ipAddress").trim();
    String masterPort = env.getProperty("master.port").trim();
    String url =
      "http://" +
      masterIpAddress +
      ":" +
      masterPort +
      "/fileinfo?file=" +
      file.trim();

    JSONObject response = get(url);
    return response;
  }

  public void downloadFile(JSONObject fileInformation) throws IOException {
    getFile(fileInformation);
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
    return responseBody;
  }

  private void getFile(JSONObject fileInformation) throws IOException {
    String ipAddress = fileInformation.getString("ipAddress");
    String port = fileInformation.getString("port");
    String fileName = fileInformation.getString("fileName");

    String url =
      "http://" + ipAddress + ":" + port + "/file?fileName=" + fileName.trim();

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(
      Collections.singletonList(MediaType.APPLICATION_OCTET_STREAM)
    );
    HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
    ResponseEntity<Resource> response = restTemplate.exchange(
      url,
      HttpMethod.GET,
      entity,
      Resource.class
    );

    if (response.getStatusCode() == HttpStatus.OK) {
      Path filePath = Paths.get("/usr/src/files/" + fileName);

      Resource fileResource = response.getBody();
      InputStream inputStream = fileResource.getInputStream();

      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
      inputStream.close();
    }
  }

  public void initFile() {
    try {
      URL url = new URL("https://picsum.photos/500/300");
      URLConnection conn = url.openConnection();
      HttpURLConnection httpConn = (HttpURLConnection) conn;

      int responseCode = httpConn.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        Random random = new Random();
        Integer randomNumber = random.nextInt(100000 - 1) + 1;
        String fileName = "file" + randomNumber.toString() + ".jpg";
        String filePath = "/usr/src/files/" + fileName;
        try (
          InputStream inputStream = conn.getInputStream();
          FileOutputStream outputStream = new FileOutputStream(filePath)
        ) {
          byte[] buffer = new byte[4096];
          int bytesRead = -1;
          while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

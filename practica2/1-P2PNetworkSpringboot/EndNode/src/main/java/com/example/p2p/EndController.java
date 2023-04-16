package com.example.p2p;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class EndController {

  @Autowired
  private Environment env;

  private EndModel end;
  private NetworkService networkService;

  public EndController() {
    this.end = new EndModel();
    this.networkService = new NetworkService();
    networkService.register();
  }

  @GetMapping("/file")
  public String getFile() {
    // TODO Entregar el archivo que el peer esta solicitando
    networkService.deliver("nombre archivo");
    return "Here is your file";
  }

  @GetMapping("/download")
  public ResponseEntity<String> downloadFile(
    @RequestParam("file") String fileName
  ) {
    try {
      // TODO Pasar la logica de la obtencion de informacion del archivo a este metodo
      networkService.getFileInformation("nombre archivo");

      HttpClient client = HttpClient.newHttpClient();
      String masterIpAddress = env.getProperty("master.ipAddress").trim();
      String masterPort = env.getProperty("master.port").trim();
      String url =
        "http://" +
        masterIpAddress +
        ":" +
        masterPort +
        "/getFile?file=" +
        fileName.trim();

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
      System.out.println(responseBody.toString(0));
      System.out.println(fileInformation.toString());

      // TODO Descargar el archivo desde el peer indicado.

      networkService.download(fileInformation);

      return ResponseEntity.ok(response.body());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}

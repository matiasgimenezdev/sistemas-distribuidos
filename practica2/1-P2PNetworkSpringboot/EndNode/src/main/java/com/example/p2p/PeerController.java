package com.example.p2p;

import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class PeerController {

  private Peer peer;
  private NetworkService networkService;

  @Autowired
  public PeerController(NetworkService networkService, Peer peer) {
    this.peer = peer;
    this.networkService = networkService;
  }

  @GetMapping("/register")
  public ResponseEntity<String> register() {
    try {
      String[] availableFiles = this.peer.getAvailableFiles();
      JSONObject registerInformation =
        this.networkService.register(availableFiles);
      if (registerInformation.has("error")) {
        throw new Exception(registerInformation.getString("error"));
      }
      return ResponseEntity.ok(registerInformation.getString("response"));
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
  }

  @GetMapping("/download")
  public ResponseEntity<String> download(@RequestParam String fileName) {
    try {
      JSONObject fileInformation = networkService.getFileInformation(fileName);
      System.out.println(fileInformation.toString());
      if (fileInformation.has("error")) {
        throw new FileNotFoundException(fileInformation.getString("error"));
      }
      this.networkService.downloadFile(fileInformation);
      return ResponseEntity.ok(fileInformation.toString());
    } catch (Exception exception) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
    }
  }

  @GetMapping("/file")
  public ResponseEntity<Resource> get(@RequestParam String fileName) {
    try {
      Path currentPath = Paths.get("");
      String currentDir = currentPath.toAbsolutePath().toString();
      Resource file = new FileSystemResource(currentDir + "/files/" + fileName);
      System.out.println(currentDir + "/files/" + fileName);
      if (!file.exists()) { // Verificar si el archivo existe
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Devolver una respuesta 404 Not Found si no se encontró el archivo
      }

      return ResponseEntity
        .ok()
        .header(
          HttpHeaders.CONTENT_DISPOSITION,
          "attachment; filename=\"" + file.getFilename() + "\""
        )
        .body(file); // TODO Ver si esta enviando bien el archivo en la respuesta
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }
}
